package org.example.linkshorter.service.control.impl;

import org.example.linkshorter.dto.ClickDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserEditDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.mapping.LinkMapper;
import org.example.linkshorter.mapping.UserMapper;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.click.PagingClickService;
import org.example.linkshorter.service.exception.TokenNotFoundException;
import org.example.linkshorter.service.exception.UserNotFoundException;
import org.example.linkshorter.service.link.DestroyLinkService;
import org.example.linkshorter.service.link.PagingShortLinkService;
import org.example.linkshorter.service.user.UpdateUserService;
import org.example.linkshorter.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private AuthUtil authUtil;
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @Mock
    private UpdateUserService updateUserService;
    @Mock
    private DestroyLinkService destroyLinkService;
    @Mock
    private PagingShortLinkService pagingShortLinkService;
    @Mock
    private PagingClickService pagingClickService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private LinkMapper linkMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserInfoWithExistingUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setBanned(false);

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(userMapper.userToUserInfoDto(user)).thenReturn(new UserInfoDto());

        UserInfoDto userInfoDto = userService.getUserInfo();

        assertNotNull(userInfoDto);
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(userMapper, times(1)).userToUserInfoDto(user);
    }

    @Test
    void testGetUserInfoWithoutExistingUser() {
        when(authUtil.getUserFromAuthContext()).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUserInfo());
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(userMapper, never()).userToUserInfoDto(any());
    }

    @Test
    void testUpdateUser() {
        UserEditDto userEditDto = new UserEditDto();
        userEditDto.setEmail("new@test.com");
        userEditDto.setPassword("newPassword");

        userService.updateUser(userEditDto);

        verify(updateUserService, times(1)).update("new@test.com", "newPassword");
    }

    @Test
    void testGetUserTokens() {
        User user = new User();
        user.setId(1L);

        Pageable pageable = Pageable.unpaged();
        Page<ShortLink> shortLinkPage = new PageImpl<>(Collections.singletonList(new ShortLink()));

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(pagingShortLinkService.findByUserId(1L, pageable)).thenReturn(shortLinkPage);
        when(linkMapper.shortLinkToShortLinkDto(any())).thenReturn(new ShortLinkDto());

        Page<ShortLinkDto> result = userService.getUserTokens(pageable);

        assertNotNull(result);
        assertEquals(shortLinkPage.getTotalElements(), result.getTotalElements());
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(pagingShortLinkService, times(1)).findByUserId(1L, pageable);
        verify(linkMapper, times(1)).shortLinkToShortLinkDto(any());
    }

    @Test
    void testGetUserTokenClicks() {
        User user = new User();
        user.setId(1L);
        ShortLink shortLink = new ShortLink();
        shortLink.setId(1L);
        shortLink.setUser(user);

        Pageable pageable = Pageable.unpaged();
        Page<Click> clickPage = new PageImpl<>(Collections.singletonList(new Click()));

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(shortLinkRepository.findById(1L)).thenReturn(Optional.of(shortLink));
        when(pagingClickService.findByTokenId(1L, pageable)).thenReturn(clickPage);
        when(linkMapper.clickTpClickDto(any())).thenReturn(new ClickDto());

        Page<ClickDto> result = userService.getUserTokenClicks(1L, pageable);

        assertNotNull(result);
        assertEquals(clickPage.getTotalElements(), result.getTotalElements());
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(shortLinkRepository, times(1)).findById(1L);
        verify(pagingClickService, times(1)).findByTokenId(1L, pageable);
        verify(linkMapper, times(1)).clickTpClickDto(any());
    }

    @Test
    void testGetUserTokenClicksWithInvalidTokenId() {
        User user = new User();
        user.setId(1L);

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(shortLinkRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> userService.getUserTokenClicks(2L, Pageable.unpaged()));
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(shortLinkRepository, times(1)).findById(2L);
        verify(pagingClickService, never()).findByTokenId(anyLong(), any());
    }

    @Test
    void testDeleteUserToken() {
        User user = new User();
        user.setId(1L);
        ShortLink shortLink = new ShortLink();
        shortLink.setId(1L);
        shortLink.setUser(user);

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(shortLinkRepository.findById(1L)).thenReturn(Optional.of(shortLink));

        userService.deleteUserToken(1L);

        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(shortLinkRepository, times(1)).findById(1L);
        verify(destroyLinkService, times(1)).deleteToken(1L);
    }

    @Test
    void testDeleteUserTokenWithInvalidTokenId() {
        User user = new User();
        user.setId(1L);

        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(shortLinkRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> userService.deleteUserToken(2L));
        verify(authUtil, times(1)).getUserFromAuthContext();
        verify(shortLinkRepository, times(1)).findById(2L);
        verify(destroyLinkService, never()).deleteToken(anyLong());
    }

}