package org.example.linkshorter.service.control.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.mapping.LinkMapper;
import org.example.linkshorter.mapping.UserMapper;
import org.example.linkshorter.service.control.AdminService;
import org.example.linkshorter.service.link.BanLinkService;
import org.example.linkshorter.service.link.DestroyLinkService;
import org.example.linkshorter.service.link.PagingLongLinkService;
import org.example.linkshorter.service.link.PagingShortLinkService;
import org.example.linkshorter.service.user.PagingUserService;
import org.example.linkshorter.service.user.UserBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminServiceImpl implements AdminService {
    private final PagingUserService pagingUserService;
    private final PagingShortLinkService pagingShortLinkService;
    private final PagingLongLinkService pagingLongLinkService;
    private final DestroyLinkService destroyLinkService;
    private final UserBanService userBanService;
    private final BanLinkService banLinkService;
    private final UserMapper userMapper;
    private final LinkMapper linkMapper;

    @Autowired
    public AdminServiceImpl(PagingUserService pagingUserService, PagingShortLinkService pagingShortLinkService, PagingLongLinkService pagingLongLinkService, DestroyLinkService destroyLinkService, UserBanService userBanService, BanLinkService banLinkService, UserMapper userMapper, LinkMapper linkMapper) {
        this.pagingUserService = pagingUserService;
        this.pagingShortLinkService = pagingShortLinkService;
        this.pagingLongLinkService = pagingLongLinkService;
        this.destroyLinkService = destroyLinkService;
        this.userBanService = userBanService;
        this.banLinkService = banLinkService;
        this.userMapper = userMapper;
        this.linkMapper = linkMapper;
    }

    @Override
    public Page<UserInfoDto> getUsersByParameter(Long userId, String username, Pageable pageable) {
        if (userId != null) {
            return new PageImpl<>(Collections.singletonList(getUserById(userId)));
        } else if (!StringUtils.isEmpty(username)) {
            return new PageImpl<>(Collections.singletonList(getUserByUsername(username)));
        } else {
            return getAllUsers(pageable);
        }
    }

    @Override
    public Page<ShortLinkDto> getTokensByParameter(Long userId, String username, String token, Pageable pageable) {
        if (userId != null) {
            return getAllShortLinksByUserId(userId, pageable);
        } else if (!StringUtils.isEmpty(username)) {
            return getAllShortLinksByUsername(username, pageable);
        } else if (!StringUtils.isEmpty(token)) {
            return new PageImpl<>(Collections.singletonList(getShortLinkByToken(token)));
        } else
            return getAllShortLinks(pageable);
    }

    @Override
    public Page<LongLinkDto> getLongLinksByParameter(Long id, String token, String username, Pageable pageable) {
        if (id != null) {
            return new PageImpl<>(Collections.singletonList(getLongLinkById(id)));
        } else if (!StringUtils.isEmpty(token)) {
            return new PageImpl<>(Collections.singletonList(getLongLinkByToken(token)));
        } else if (!StringUtils.isEmpty(username)) {
            return getAllLongLinksByUsername(username, pageable);
        } else
            return getAllLongLinks(pageable);
    }

    @Override
    public void deleteShortLink(Long shortLinkId) {
        destroyLinkService.deleteToken(shortLinkId);
    }

    @Override
    public void changeUserBanStatus(Long userId, boolean banStatus) {
        if (banStatus) {
            userBanService.banById(userId);
        } else {
            userBanService.unbanById(userId);
        }
    }

    @Override
    public void changeLongLinkForbiddenStatus(Long longLinkId, boolean forbiddenStatus) {
        if (forbiddenStatus) {
            banLinkService.banById(longLinkId);
        } else {
            banLinkService.unbanById(longLinkId);
        }
    }


    private Page<UserInfoDto> getAllUsers(Pageable pageable) {
        return pagingUserService.findAll(pageable).map(userMapper::userToUserInfoDto);
    }


    private UserInfoDto getUserById(Long userId) {
        return userMapper.userToUserInfoDto(pagingUserService.findById(userId));
    }


    private UserInfoDto getUserByUsername(String username) {
        return userMapper.userToUserInfoDto(pagingUserService.findByUsername(username));
    }


    private Page<ShortLinkDto> getAllShortLinks(Pageable pageable) {
        return pagingShortLinkService.findAll(pageable).map(linkMapper::shortLinkToShortLinkDto);
    }


    private Page<ShortLinkDto> getAllShortLinksByUserId(Long userId, Pageable pageable) {
        return pagingShortLinkService.findByUserId(userId, pageable).map(linkMapper::shortLinkToShortLinkDto);
    }


    private Page<ShortLinkDto> getAllShortLinksByUsername(String username, Pageable pageable) {
        return pagingShortLinkService.findByUsername(username, pageable).map(linkMapper::shortLinkToShortLinkDto);
    }


    private ShortLinkDto getShortLinkByToken(String token) {
        return linkMapper.shortLinkToShortLinkDto(pagingShortLinkService.findByToken(token));
    }


    private Page<LongLinkDto> getAllLongLinks(Pageable pageable) {
        return pagingLongLinkService.findAll(pageable).map(linkMapper::longLinkToLongLinkDto);
    }


    private Page<LongLinkDto> getAllLongLinksByUsername(String username, Pageable pageable) {
        return pagingLongLinkService.findAllByUsername(username, pageable).map(linkMapper::longLinkToLongLinkDto);
    }


    private LongLinkDto getLongLinkByToken(String token) {
        return linkMapper.longLinkToLongLinkDto(pagingLongLinkService.findByToken(token));
    }


    private LongLinkDto getLongLinkById(Long id) {
        return linkMapper.longLinkToLongLinkDto(pagingLongLinkService.findById(id));
    }

}
