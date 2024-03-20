package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.service.exception.LinkNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BanLinkServiceImplTest {
    @Mock
    LongLinkRepository longLinkRepository;
    @InjectMocks
    BanLinkServiceImpl banLinkService;

    @Test
    public void testBanById(){
        Long linkId = 1L;
        LongLink longLink = new LongLink();
        when(longLinkRepository.findById(linkId)).thenReturn(Optional.of(longLink));

        banLinkService.banById(linkId);

        verify(longLinkRepository, times(1)).save(longLink);
        assertTrue(longLink.isForbidden());
    }

    @Test
    public void testUnbanById(){
        Long linkId = 1L;
        LongLink longLink = new LongLink();
        longLink.setForbidden(true);
        when(longLinkRepository.findById(linkId)).thenReturn(Optional.of(longLink));

        banLinkService.unbanById(linkId);

        verify(longLinkRepository, times(1)).save(longLink);
        assertFalse(longLink.isForbidden());
    }

    @Test
    public void testBanByLongLink(){
        String url = "http://localhost:123/get";
        LongLink longLink = new LongLink();
        when(longLinkRepository.findByLongLink(url)).thenReturn(Optional.of(longLink));

        banLinkService.banByLongLink(url);

        verify(longLinkRepository, times(1)).save(longLink);
        assertTrue(longLink.isForbidden());
    }

    @Test
    public void testUnbanByLongLink(){
        String url = "http://localhost:123/get";
        LongLink longLink = new LongLink();
        longLink.setForbidden(true);
        when(longLinkRepository.findByLongLink(url)).thenReturn(Optional.of(longLink));

        banLinkService.unbanByLongLink(url);

        verify(longLinkRepository, times(1)).save(longLink);
        assertFalse(longLink.isForbidden());
    }

    @Test
    public void testWithNoExistingLongLink(){
        Long linkId = 1L;

        when(longLinkRepository.findById(linkId)).thenReturn(Optional.empty());

        assertThrows(LinkNotFoundException.class, () -> {
            banLinkService.unbanById(linkId);
        });

    }


}