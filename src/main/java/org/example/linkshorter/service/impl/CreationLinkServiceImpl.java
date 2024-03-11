package org.example.linkshorter.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.CreationLinkService;
import org.example.linkshorter.util.LinkValidator;
import org.example.linkshorter.util.TokenGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CreationLinkServiceImpl implements CreationLinkService {
    private final LongLinkRepository longLinkRepository;
    private final ShortLinkRepository shortLinkRepository;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final LinkValidator linkValidator;
    private final Random random;

    public CreationLinkServiceImpl(LongLinkRepository longLinkRepository, ShortLinkRepository shortLinkRepository, UserRepository userRepository, TokenGenerator tokenGenerator, LinkValidator linkValidator) {
        this.longLinkRepository = longLinkRepository;
        this.shortLinkRepository = shortLinkRepository;
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.linkValidator = linkValidator;
        this.random = new Random();
    }

    @Override
    @Transactional
    public void addLink(String longLink, String token) {
        if (isEmptyToken(token)) {
            addLink(longLink);
        } else {
            createShortLink(longLink, token);
        }
    }

    @Override
    @Transactional
    public void addLink(String longLink) {
        createShortLink(longLink, generateToken(longLink));
    }


    private void createShortLink(String longLink, String token) {
        validateLink(longLink);
        User user = getUserFromAuthContext();
        LongLink url = longLinkRepository.findByLongLink(longLink).orElse(new LongLink(longLink));
        ShortLink shortLink = new ShortLink();
        shortLink.setLongLink(url);
        shortLink.setToken(token);
        shortLink.setUser(user);
        shortLink.setCreationDate(LocalDateTime.now());
        shortLink.setExpirationDate(user == null ? LocalDateTime.now().plusDays(3) : null);
        saveLinkAndToken(shortLink, url, user);
    }

    private void saveLinkAndToken(ShortLink shortLink, LongLink existingLink, User user) {
        existingLink.getShortLinks().add(shortLink);
        if (user != null) {
            user.getShortLinks().add(shortLink);
        }
        shortLinkRepository.save(shortLink);
    }

    private String generateToken(String longLink) {
        String token = tokenGenerator.generateTokenForString(longLink);
        while (shortLinkRepository.existsByToken(token)) {
            token = tokenGenerator.generateTokenForString(longLink + random.nextLong());
        }
        return token;
    }

    private User getUserFromAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            return user.orElse(null);
        } else {
            return null;
        }
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
