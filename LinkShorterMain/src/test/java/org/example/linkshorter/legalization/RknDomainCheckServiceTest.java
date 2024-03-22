package org.example.linkshorter.legalization;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.service.link.BanLinkService;
import org.example.linkshorter.util.LinkValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RknDomainCheckServiceTest {
    @Mock
    private LongLinkRepository longLinkRepository;
    @Mock
    private LinkValidator linkValidator;
    @Mock
    private BanLinkService banLinkService;
    @Mock
    private RknDomainLoader rknDomainLoader;
    @InjectMocks
    private RknDomainCheckService domainCheckService;

    @Test
    void testCheckLongLinkDomains() throws DomainsNotFoundException {
        List<String> blockedDomains = List.of("blocked-domain.com");
        LongLink longLink1 = new LongLink("https://non-blocked-domain.com");
        LongLink longLink2 = new LongLink("https://blocked-domain.com");

        when(linkValidator.getDomain("https://non-blocked-domain.com")).thenReturn("non-blocked-domain.com");
        when(linkValidator.getDomain("https://blocked-domain.com")).thenReturn("blocked-domain.com");
        when(rknDomainLoader.loadDomains()).thenReturn(blockedDomains);
        when(longLinkRepository.findAllByForbiddenFalse()).thenReturn(List.of(longLink1, longLink2));

        domainCheckService.checkLongLinkDomains();

        verify(banLinkService, times(1)).banByLongLink("https://blocked-domain.com");
        verify(banLinkService, never()).banByLongLink("non-blocked-domain.com");
        verify(linkValidator, atLeast(2)).getDomain(anyString());
    }

    @Test
    void testCheckNonBlockedLongLinkDomains() throws DomainsNotFoundException {
        List<String> blockedDomains = new ArrayList<>();
        LongLink longLink1 = new LongLink("https://non-blocked-domain.com");

        when(linkValidator.getDomain("https://non-blocked-domain.com")).thenReturn("non-blocked-domain.com");
        when(rknDomainLoader.loadDomains()).thenReturn(blockedDomains);
        when(longLinkRepository.findAllByForbiddenFalse()).thenReturn(List.of(longLink1));

        domainCheckService.checkLongLinkDomains();

        verify(banLinkService, never()).banByLongLink(any());
        verify(linkValidator, atLeast(1)).getDomain(anyString());
    }
}