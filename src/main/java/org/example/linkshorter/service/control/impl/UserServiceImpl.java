package org.example.linkshorter.service.control.impl;

import org.example.linkshorter.dto.ClickDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserEditDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.mapping.LinkMapper;
import org.example.linkshorter.mapping.UserMapper;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.click.PagingClickService;
import org.example.linkshorter.service.control.UserService;
import org.example.linkshorter.service.exception.TokenNotFoundException;
import org.example.linkshorter.service.exception.UserNotFoundException;
import org.example.linkshorter.service.link.DestroyLinkService;
import org.example.linkshorter.service.link.PagingShortLinkService;
import org.example.linkshorter.service.user.UpdateUserService;
import org.example.linkshorter.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final AuthUtil authUtil;
    private final ShortLinkRepository shortLinkRepository;
    private final UpdateUserService updateUserService;
    private final DestroyLinkService destroyLinkService;
    private final PagingShortLinkService pagingShortLinkService;
    private final PagingClickService pagingClickService;
    private final UserMapper userMapper;
    private final LinkMapper linkMapper;

    @Autowired
    public UserServiceImpl(AuthUtil authUtil, ShortLinkRepository shortLinkRepository, UpdateUserService updateUserService, DestroyLinkService destroyLinkService, PagingShortLinkService pagingShortLinkService, PagingClickService pagingClickService, UserMapper userMapper, LinkMapper linkMapper) {
        this.authUtil = authUtil;
        this.shortLinkRepository = shortLinkRepository;
        this.updateUserService = updateUserService;
        this.destroyLinkService = destroyLinkService;
        this.pagingShortLinkService = pagingShortLinkService;
        this.pagingClickService = pagingClickService;
        this.userMapper = userMapper;
        this.linkMapper = linkMapper;
    }

    @Override
    @ServiceLogging
    public UserInfoDto getUserInfo() {
        User user = getUser();
        return userMapper.userToUserInfoDto(user);
    }

    @Override
    @ServiceLogging
    public void updateUser(UserEditDto updateInfo) {
        updateUserService.update(updateInfo.getEmail(), updateInfo.getPassword());
    }

    @Override
    @ServiceLogging
    public Page<ShortLinkDto> getUserTokens(Pageable pageable) {
        User user = getUser();
        return pagingShortLinkService.findByUserId(user.getId(), pageable).map(linkMapper::shortLinkToShortLinkDto);
    }

    @Override
    @ServiceLogging
    public Page<ClickDto> getUserTokenClicks(Long tokenId, Pageable pageable) {
        User user = getUser();
        if (shortLinkRepository.findById(tokenId).filter(x -> x.getUser().equals(user)).isPresent()) {
            return pagingClickService.findByTokenId(tokenId, pageable).map(linkMapper::clickTpClickDto);
        } else {
            throw new TokenNotFoundException("У пользователя отсутсвует токен с id: " + tokenId);
        }
    }

    @Override
    @ServiceLogging
    public void deleteUserToken(Long tokenId) {
        User user = getUser();
        if (shortLinkRepository.findById(tokenId).filter(x -> x.getUser().equals(user)).isPresent()) {
            destroyLinkService.deleteToken(tokenId);
        } else {
            throw new TokenNotFoundException("У пользователя отсутсвует токен с id: " + tokenId);
        }
    }

    private User getUser() {
        User user = authUtil.getUserFromAuthContext();
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return user;
    }

}
