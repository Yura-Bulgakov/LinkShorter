package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.entity.UserRole;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.security.UserDetailsImpl;
import org.example.linkshorter.util.LinkValidator;
import org.example.linkshorter.util.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class LinkServiceImplTest {
    @Mock
    private LongLinkRepository longLinkRepository;
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenGenerator tokenGenerator;
    @Mock
    private LinkValidator linkValidator;
    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    void testAddLinkWithUser() {
        String longLink = "https://yury.com";
        User user = new User(1L, "username", "password", "email@mail.ru",
                UserRole.ROLE_USER);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(user));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(linkValidator.isValid(longLink)).thenReturn(true);

        linkService.addLink(longLink);

        verify(longLinkRepository, times(1)).save(any(LongLink.class));
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void testAddLinkWithoutUser() {
        String longLink = "https://yury.com";
        when(linkValidator.isValid(longLink)).thenReturn(true);
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        linkService.addLink(longLink);
        verify(longLinkRepository, times(1)).save(any(LongLink.class));
        verify(userRepository, times(0)).findByUsername(any(String.class));
    }

    @Test
    void testAddLinkWithExistingLinkAndUser() {
        String longLink = "https://yury.com";
        LongLink existingLink = new LongLink(longLink);
        User user = new User(1L, "username", "password", "email@mail.ru",
                UserRole.ROLE_USER);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(user));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(longLinkRepository.findByLongLink(longLink)).thenReturn(Optional.of(existingLink));

        linkService.addLink(longLink);

        verify(longLinkRepository, times(1)).save(existingLink);

        assertTrue(existingLink.getUsers().contains(user));
        assertTrue(user.getLongLinks().contains(existingLink));
    }

    @Test
    void testAddEmptyLink() {
        String invalidLink = "";
        assertThrows(IllegalArgumentException.class, () -> linkService.addLink(invalidLink));
    }

    @Test
    void testAddInvalidLink() {
        String invalidLink = "dasd";
        when(linkValidator.isValid(invalidLink)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> linkService.addLink(invalidLink));
    }

    @Test
    void testAddTokenToExistingLink() {
        String longLink = "https://yury.com";
        LongLink longLinkEntity = new LongLink(longLink);
        String token = "token";
        when(longLinkRepository.findByLongLink(longLink)).thenReturn(Optional.of(longLinkEntity));
        when(tokenGenerator.generateTokenForString(longLink)).thenReturn(token);
        linkService.addToken(longLink, null);
        verify(shortLinkRepository, times(1)).save(any(ShortLink.class));
    }

    @Test
    void testAddExistingTokenToLink() {
        String longLink = "https://yury.com";
        String existingToken = "token";
        LongLink longLinkEntity = new LongLink(longLink);
        when(longLinkRepository.findByLongLink(longLink)).thenReturn(Optional.of(longLinkEntity));
        when(tokenGenerator.generateTokenForString(longLink)).thenReturn(existingToken);
        when(shortLinkRepository.existsByToken(existingToken)).thenReturn(true);

        linkService.addToken(longLink, null);

        verify(tokenGenerator, atLeast(2)).generateTokenForString(any(String.class));
        verify(shortLinkRepository, times(1)).save(any(ShortLink.class));
    }

    @Test
    void testDeleteLongLink() {
        String longLink = "https://yury.com";
        linkService.deleteLink(longLink);
        verify(longLinkRepository, times(1)).deleteByLongLink(longLink);
    }

    @Test
    void testDeleteToken() {
        String token = "token";
        linkService.deleteToken(token);
        verify(shortLinkRepository, times(1)).deleteByToken(token);
    }
}