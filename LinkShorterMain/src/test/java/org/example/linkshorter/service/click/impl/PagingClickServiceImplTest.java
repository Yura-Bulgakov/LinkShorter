package org.example.linkshorter.service.click.impl;

import org.example.linkshorter.entity.Click;
import org.example.linkshorter.repository.ClickRepository;
import org.example.linkshorter.service.click.PagingClickServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagingClickServiceImplTest {
    @Mock
    ClickRepository clickRepository;

    @InjectMocks
    PagingClickServiceImpl pagingClickService;

    @Test
    public void testFindByTokenId() {
        Long tokenId = 1L;
        Pageable pageable = Pageable.unpaged();
        List<Click> clicks = Arrays.asList(new Click(), new Click());

        when(clickRepository.findByShortLinkId(tokenId, pageable)).thenReturn(new PageImpl<>(clicks) {
        });

        Page<Click> result = pagingClickService.findByTokenId(tokenId, pageable);

        assertEquals(clicks.size(), result.getContent().size());
    }

}