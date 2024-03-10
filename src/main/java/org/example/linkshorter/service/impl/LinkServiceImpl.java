package org.example.linkshorter.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.CreationLinkService;
import org.example.linkshorter.service.DestroyLinkService;
import org.example.linkshorter.service.exception.LinkNotFoundException;
import org.example.linkshorter.util.LinkValidator;
import org.example.linkshorter.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkServiceImpl implements CreationLinkService, DestroyLinkService {
    private final LongLinkRepository longLinkRepository;
    private final ShortLinkRepository shortLinkRepository;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final LinkValidator linkValidator;
    private final Random random;

    @Autowired
    public LinkServiceImpl(LongLinkRepository longLinkRepository, ShortLinkRepository shortLinkRepository,
                           UserRepository userRepository, TokenGenerator tokenGenerator, LinkValidator linkValidator) {
        this.longLinkRepository = longLinkRepository;
        this.shortLinkRepository = shortLinkRepository;
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.linkValidator = linkValidator;
        random = new Random();
    }

    @Override
    @Transactional
    public void addLink(String longLink) {
        validateLink(longLink);
        User user = getUserFromAuthContext();
        LongLink existingLink = longLinkRepository.findByLongLink(longLink)
                .orElse(null);
        if (existingLink != null && user != null) {
            addExistingLinkToUser(existingLink, user);
        } else {
            saveNewLink(longLink, user);
        }
    }

    @Override
    @Transactional
    public void addToken(String longLink, String token) {
        if (StringUtils.isEmpty(token)) {
            token = generateToken(longLink);
        }
        LongLink linkForToken = longLinkRepository.findByLongLink(longLink)
                .orElseThrow(() -> new LinkNotFoundException("Ссылка для токена не найдена:" + longLink));
        saveNewToken(token, linkForToken);
    }

    @Override
    @Transactional
    public void deleteLink(String longLink) {
        longLinkRepository.deleteByLongLink(longLink);
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        shortLinkRepository.deleteByToken(token);
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

    private void addExistingLinkToUser(LongLink existingLink, User user) {
        existingLink.getUsers().add(user);
        user.getLongLinks().add(existingLink);
        longLinkRepository.save(existingLink);
    }

    private void saveNewLink(String longLink, User user) {
        LongLink newLink = new LongLink(longLink);
        if (user != null) {
            newLink.getUsers().add(user);
            user.getLongLinks().add(newLink);
        }
        longLinkRepository.save(newLink);
    }

    private void saveNewToken(String token, LongLink linkForToken) {
        User user = getUserFromAuthContext();
        ShortLink newToken = new ShortLink();
        newToken.setToken(token);
        newToken.setLongLink(linkForToken);
        newToken.setUser(user);
        newToken.setCreationDate(LocalDateTime.now());
        newToken.setExpirationDate(user == null ? LocalDateTime.now().plusDays(3) : null);
        shortLinkRepository.save(newToken);
    }

}
