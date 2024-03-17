package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagingShortLinkServiceImplTest {
    @Mock
    ShortLinkRepository shortLinkRepository;

    @InjectMocks
    PagingShortLinkServiceImpl pagingLinkService;

    @Test
    public void testFindByUserId() {
        Long userId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<ShortLink> shortLinks = Arrays.asList(new ShortLink(), new ShortLink());

        when(shortLinkRepository.findByUserId(userId, pageable)).thenReturn(new PageImpl<>(shortLinks));

        Page<ShortLink> result = pagingLinkService.findByUserId(userId, pageable);

        assertEquals(shortLinks.size(), result.getContent().size());
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        Pageable pageable = Pageable.unpaged();
        List<ShortLink> shortLinks = Arrays.asList(new ShortLink(), new ShortLink());

        when(shortLinkRepository.findByUserUsername(username, pageable)).thenReturn(new PageImpl<>(shortLinks));

        Page<ShortLink> result = pagingLinkService.findByUsername(username, pageable);

        assertEquals(shortLinks.size(), result.getContent().size());
    }

    @Test
    public void testFindByToken() {
        String token = "token";
        ShortLink shortLink = new ShortLink();

        when(shortLinkRepository.findByToken(token)).thenReturn(Optional.of(shortLink));

        ShortLink result = pagingLinkService.findByToken(token);

        assertEquals(shortLink, result);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = Pageable.unpaged();
        List<ShortLink> shortLinks = Arrays.asList(new ShortLink(), new ShortLink());

        when(shortLinkRepository.findAll(pageable)).thenReturn(new PageImpl<>(shortLinks));

        Page<ShortLink> result = pagingLinkService.findAll(pageable);

        assertEquals(shortLinks.size(), result.getContent().size());
    }
}