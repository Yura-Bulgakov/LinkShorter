package org.example.linkshorter.service.link.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.link.CreationLinkService;
import org.example.linkshorter.util.AuthUtil;
import org.example.linkshorter.util.LinkValidator;
import org.example.linkshorter.util.TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CreationLinkServiceImpl implements CreationLinkService {
    private final LongLinkRepository longLinkRepository;
    private final ShortLinkRepository shortLinkRepository;
    private final TokenGenerator tokenGenerator;
    private final LinkValidator linkValidator;
    private final AuthUtil authUtil;
    private final Random random;

    public CreationLinkServiceImpl(LongLinkRepository longLinkRepository, ShortLinkRepository shortLinkRepository, TokenGenerator tokenGenerator, LinkValidator linkValidator, AuthUtil authUtil) {
        this.longLinkRepository = longLinkRepository;
        this.shortLinkRepository = shortLinkRepository;
        this.tokenGenerator = tokenGenerator;
        this.linkValidator = linkValidator;
        this.authUtil = authUtil;
        this.random = new Random();
    }

    @Override
    @Transactional
    @ServiceLogging
    public ShortLink addLink(String longLink, String token) {
        if (isEmptyToken(token)) {
            return addLink(longLink);
        } else {
            return createShortLink(longLink, token);
        }
    }

    @Override
    @Transactional
    @ServiceLogging
    public ShortLink addLink(String longLink) {
        return createShortLink(longLink, generateToken(longLink));
    }


    private ShortLink createShortLink(String longLink, String token) {
        validateLink(longLink);
        User user = authUtil.getUserFromAuthContext();
        LongLink url = longLinkRepository.findByLongLink(longLink).orElseGet(() ->
                longLinkRepository.save(new LongLink(longLink)));
        ShortLink shortLink = new ShortLink();
        shortLink.setLongLink(url);
        shortLink.setToken(token);
        shortLink.setUser(user);
        shortLink.setCreationDate(LocalDateTime.now());
        shortLink.setExpirationDate(user == null ? LocalDateTime.now().plusDays(3) : null);
        return saveLinkAndToken(shortLink, url, user);
    }

    private ShortLink saveLinkAndToken(ShortLink shortLink, LongLink existingLink, User user) {
        existingLink.getShortLinks().add(shortLink);
        if (user != null) {
            user.getShortLinks().add(shortLink);
        }
        return shortLinkRepository.save(shortLink);
    }

    private String generateToken(String longLink) {
        String token = tokenGenerator.generateTokenForString(longLink);
        while (shortLinkRepository.existsByToken(token)) {
            token = tokenGenerator.generateTokenForString(longLink + random.nextLong());
        }
        return token;
    }

    private void validateLink(String link) {
        if (StringUtils.isEmpty(link)) {
            throw new IllegalArgumentException("Ссылка не должна быть пустой");
        } else if (!linkValidator.isValid(link)) {
            throw new IllegalArgumentException("Не валидная ссылка: " + link);
        }
    }

    private boolean isEmptyToken(String token) {
        return StringUtils.isEmpty(token);
    }

}
