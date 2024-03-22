package org.example.linkshorter.service.control.impl;

import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.mapping.LinkMapper;
import org.example.linkshorter.mapping.UserMapper;
import org.example.linkshorter.service.link.BanLinkService;
import org.example.linkshorter.service.link.DestroyLinkService;
import org.example.linkshorter.service.link.PagingLongLinkService;
import org.example.linkshorter.service.link.PagingShortLinkService;
import org.example.linkshorter.service.user.PagingUserService;
import org.example.linkshorter.service.user.UserBanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private PagingUserService pagingUserService;
    @Mock
    private PagingShortLinkService pagingShortLinkService;
    @Mock
    private PagingLongLinkService pagingLongLinkService;
    @Mock
    private DestroyLinkService destroyLinkService;
    @Mock
    private UserBanService userBanService;
    @Mock
    private BanLinkService banLinkService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private LinkMapper linkMapper;
    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void testGetUsersByParameterWithUserId() {
        User user = new User();
        user.setId(1L);
        UserInfoDto userInfoDto = new UserInfoDto();

        when(pagingUserService.findById(1L)).thenReturn(user);
        when(userMapper.userToUserInfoDto(user)).thenReturn(userInfoDto);

        Page<UserInfoDto> result = adminService.getUsersByParameter(1L, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userInfoDto, result.getContent().get(0));
        verify(pagingUserService, times(1)).findById(1L);
        verify(userMapper, times(1)).userToUserInfoDto(user);
    }

    @Test
    void testGetUsersByParameterWithUserIdAndUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        UserInfoDto userInfoDto = new UserInfoDto();

        when(pagingUserService.findById(1L)).thenReturn(user);
        when(userMapper.userToUserInfoDto(user)).thenReturn(userInfoDto);

        Page<UserInfoDto> result = adminService.getUsersByParameter(1L, "user", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userInfoDto, result.getContent().get(0));
        verify(pagingUserService, times(1)).findById(1L);
        verify(pagingUserService, never()).findByUsername("user");
        verify(userMapper, times(1)).userToUserInfoDto(user);
    }

    @Test
    void testGetUsersByParameterWithUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        UserInfoDto userInfoDto = new UserInfoDto();

        when(pagingUserService.findByUsername("user")).thenReturn(user);
        when(userMapper.userToUserInfoDto(user)).thenReturn(userInfoDto);

        Page<UserInfoDto> result = adminService.getUsersByParameter(null, "user", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userInfoDto, result.getContent().get(0));
        verify(pagingUserService, never()).findById(1L);
        verify(pagingUserService, times(1)).findByUsername("user");
        verify(userMapper, times(1)).userToUserInfoDto(user);
    }

    @Test
    void testGetUsersByParameterWithoutIdAndUsername() {
        User user = new User();
        UserInfoDto userInfoDto = new UserInfoDto();
        Page<User> page = new PageImpl<>(Collections.singletonList(user));

        when(pagingUserService.findAll(Pageable.unpaged())).thenReturn(page);
        when(userMapper.userToUserInfoDto(any(User.class))).thenReturn(userInfoDto);

        Page<UserInfoDto> result = adminService.getUsersByParameter(null, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userInfoDto, result.getContent().get(0));
        verify(pagingUserService, never()).findById(anyLong());
        verify(pagingUserService, never()).findByUsername(anyString());
        verify(pagingUserService, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    void testGetTokensByParameterWithUserId() {
        Page<ShortLink> page = new PageImpl<>(Collections.singletonList(new ShortLink()));

        when(pagingShortLinkService.findByUserId(1L, Pageable.unpaged())).thenReturn(page);

        Page<ShortLinkDto> result = adminService.getTokensByParameter(1L, null, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingShortLinkService, times(1)).findByUserId(1L, Pageable.unpaged());
    }

    @Test
    void testGetTokensByParameterWithUsername() {
        Page<ShortLink> page = new PageImpl<>(Collections.singletonList(new ShortLink()));

        when(pagingShortLinkService.findByUsername("user", Pageable.unpaged())).thenReturn(page);

        Page<ShortLinkDto> result = adminService.getTokensByParameter(null, "user", null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingShortLinkService, times(1)).findByUsername("user", Pageable.unpaged());
    }

    @Test
    void testGetTokensByParameterWithToken() {
        ShortLink shortLink = new ShortLink();
        Page<ShortLink> page = new PageImpl<>(Collections.singletonList(shortLink));

        when(pagingShortLinkService.findByToken("token")).thenReturn(shortLink);

        Page<ShortLinkDto> result = adminService.getTokensByParameter(null, null, "token", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingShortLinkService, times(1)).findByToken("token");
    }

    @Test
    void testGetTokensByParameterWithPageable() {
        ShortLink shortLink = new ShortLink();
        Page<ShortLink> page = new PageImpl<>(Collections.singletonList(shortLink));

        when(pagingShortLinkService.findAll(Pageable.unpaged())).thenReturn(page);

        Page<ShortLinkDto> result = adminService.getTokensByParameter(null, null, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingShortLinkService, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    void testGetLongLinksByParameterWithId() {
        when(pagingLongLinkService.findById(1L)).thenReturn(new LongLink());
        when(linkMapper.longLinkToLongLinkDto(any())).thenReturn(new LongLinkDto());

        Page<LongLinkDto> result = adminService.getLongLinksByParameter(1L, null, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingLongLinkService, times(1)).findById(1L);
        verify(linkMapper, times(1)).longLinkToLongLinkDto(any());
    }

    @Test
    void testGetLongLinksByParameterWithToken() {
        when(pagingLongLinkService.findByToken("token")).thenReturn(new LongLink());
        when(linkMapper.longLinkToLongLinkDto(any())).thenReturn(new LongLinkDto());

        Page<LongLinkDto> result = adminService.getLongLinksByParameter(null, "token", null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingLongLinkService, times(1)).findByToken("token");
        verify(linkMapper, times(1)).longLinkToLongLinkDto(any());
    }

    @Test
    void testGetLongLinksByParameterWithUsername() {
        Page<LongLink> page = new PageImpl<>(Collections.singletonList(new LongLink()));

        when(pagingLongLinkService.findAllByUsername("user", Pageable.unpaged())).thenReturn(page);
        when(linkMapper.longLinkToLongLinkDto(any(LongLink.class))).thenReturn(new LongLinkDto());

        Page<LongLinkDto> result = adminService.getLongLinksByParameter(null, null, "user", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingLongLinkService, times(1)).findAllByUsername("user", Pageable.unpaged());
        verify(linkMapper, times(1)).longLinkToLongLinkDto(any(LongLink.class));
    }

    @Test
    void testGetLongLinksByParameterWithPageable() {
        Page<LongLink> page = new PageImpl<>(Collections.singletonList(new LongLink()));

        when(pagingLongLinkService.findAll(Pageable.unpaged())).thenReturn(page);
        when(linkMapper.longLinkToLongLinkDto(any(LongLink.class))).thenReturn(new LongLinkDto());

        Page<LongLinkDto> result = adminService.getLongLinksByParameter(null, null, null, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(pagingLongLinkService, times(1)).findAll(Pageable.unpaged());
        verify(linkMapper, times(1)).longLinkToLongLinkDto(any(LongLink.class));
    }

    @Test
    void testDeleteShortLink() {
        adminService.deleteShortLink(1L);
        verify(destroyLinkService, times(1)).deleteToken(1L);
    }

    @Test
    void testChangeUserBanStatus_Ban() {
        adminService.changeUserBanStatus(1L, true);
        verify(userBanService, times(1)).banById(1L);
    }

    @Test
    void testChangeUserBanStatus_Unban() {
        adminService.changeUserBanStatus(1L, false);
        verify(userBanService, times(1)).unbanById(1L);
    }

    @Test
    void testChangeLongLinkForbiddenStatus_Ban() {
        adminService.changeLongLinkForbiddenStatus(1L, true);
        verify(banLinkService, times(1)).banById(1L);
    }

    @Test
    void testChangeLongLinkForbiddenStatus_Unban() {
        adminService.changeLongLinkForbiddenStatus(1L, false);
        verify(banLinkService, times(1)).unbanById(1L);
    }

}