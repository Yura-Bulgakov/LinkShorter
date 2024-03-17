package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.util.AuthUtil;
import org.example.linkshorter.util.LinkValidator;
import org.example.linkshorter.util.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreationLinkServiceImplTest {
    @Mock
    private LongLinkRepository longLinkRepository;
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @Mock
    private TokenGenerator tokenGenerator;
    @Mock
    private LinkValidator linkValidator;
    @Mock
    private AuthUtil authUtil;
    @InjectMocks
    private CreationLinkServiceImpl creationLinkService;

    @Test
    public void testAddLinkWithTokenWithAuthUser() {
        String longLink = "https://yury.com";
        String token = "token";
        User user = new User();
        user.setUsername("test");

        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(authUtil.getUserFromAuthContext()).thenReturn(user);
        when(longLinkRepository.save(any(LongLink.class))).thenReturn(new LongLink(longLink));

        creationLinkService.addLink(longLink, token);

        verify(linkValidator).isValid(longLink);
        verify(shortLinkRepository).save(any(ShortLink.class));
    }

    @Test
    public void testAddLinkWithoutTokenWithoutUser() {
        String longLink = "https://yury.com";

        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(authUtil.getUserFromAuthContext()).thenReturn(null);
        when(longLinkRepository.save(any(LongLink.class))).thenReturn(new LongLink(longLink));

        creationLinkService.addLink(longLink);

        verify(linkValidator).isValid(longLink);
        verify(shortLinkRepository).save(any(ShortLink.class));
    }

    @Test
    public void testAddExistingLinkWithToken() {
        String longLink = "https://yury.com";
        String token = "token";
        LongLink existingLink = new LongLink();
        existingLink.setLongLink(longLink);

        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(longLinkRepository.findByLongLink(longLink)).thenReturn(Optional.of(existingLink));

        creationLinkService.addLink(longLink, token);

        verify(longLinkRepository).findByLongLink(longLink);
        verify(shortLinkRepository).save(any(ShortLink.class));
    }

    @Test
    public void testAddLinkWithEmptyToken() {
        String longLink = "https://yury.com";
        String token = "";

        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(longLinkRepository.save(any(LongLink.class))).thenReturn(new LongLink(longLink));

        creationLinkService.addLink(longLink, token);

        verify(shortLinkRepository, times(1)).save(any(ShortLink.class));
    }

    @Test
    public void testAddWithGeneratingExistingToken() {
        String longLink = "https://yury.com";
        String token = "";
        String existingToken = "token";

        when(linkValidator.isValid(longLink)).thenReturn(true);
        when(tokenGenerator.generateTokenForString(longLink)).thenReturn(existingToken);
        when(shortLinkRepository.existsByToken(existingToken)).thenReturn(true);
        when(longLinkRepository.save(any(LongLink.class))).thenReturn(new LongLink(longLink));

        creationLinkService.addLink(longLink, token);

        verify(tokenGenerator, atLeast(2)).generateTokenForString(anyString());
        verify(shortLinkRepository, times(1)).existsByToken(existingToken);
        verify(shortLinkRepository, times(1)).save(any(ShortLink.class));
    }

    @Test
    public void testAddLinkWithEmptyLink() {
        assertThrows(IllegalArgumentException.class, () -> creationLinkService.addLink("", "token"));
    }

    @Test
    public void testAddLinkWithInvalidLink() {
        String longLink = "invalid_link";
        when(linkValidator.isValid(longLink)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> creationLinkService.addLink(longLink, "token"));
    }

}
