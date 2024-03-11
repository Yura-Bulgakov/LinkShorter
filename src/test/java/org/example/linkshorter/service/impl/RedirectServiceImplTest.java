package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.repository.ClickRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.exception.LinkNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class RedirectServiceImplTest {
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @Mock
    private ClickRepository clickRepository;
    @InjectMocks
    private RedirectServiceImpl redirectService;

    @Test
    void testValidRedirect() {
        String token = "validToken";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        LongLink longLink = new LongLink("https://www.baeldung.com/spring-redirect-and-forward");
        ShortLink shortLink = new ShortLink();
        shortLink.setToken(token);
        shortLink.setLongLink(longLink);

        when(shortLinkRepository.findByToken(token)).thenReturn(Optional.of(shortLink));
        String redirectUrl = redirectService.redirect(token, request);
        assertEquals(redirectUrl, "redirect:" + shortLink.getLongLink().getLongLink());
        verify(clickRepository, times(1)).save(any(Click.class));
    }

    @Test
    void testRedirectByInvalidToken() {
        String invalidToken = "invalidToken";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(shortLinkRepository.findByToken(invalidToken)).thenReturn(Optional.empty());
        assertThrows(LinkNotFoundException.class, () -> redirectService.redirect(invalidToken, request));
    }

    @Test
    void testRedirectByNullToken() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        assertThrows(IllegalArgumentException.class, () -> redirectService.redirect(null, request));
    }

    @Test
    void testRedirectByEmptyToken() {
        String emptyToken = "";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        assertThrows(IllegalArgumentException.class, () -> redirectService.redirect(emptyToken, request));
    }

}