package org.example.linkshorter.service.impl;

import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DestroyLinkServiceImplTest {
    @Mock
    private LongLinkRepository longLinkRepository;
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @InjectMocks
    private DestroyLinkServiceImpl linkService;

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