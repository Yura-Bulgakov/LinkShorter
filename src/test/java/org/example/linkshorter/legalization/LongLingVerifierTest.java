package org.example.linkshorter.legalization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LongLingVerifierTest {
    @Mock
    private DomainCheckService domainCheckService;

    @InjectMocks
    private LongLingVerifier longLingVerifier;

    @Test
    void testScheduledCheck() throws DomainsNotFoundException {
        longLingVerifier.scheduledCheck();
        verify(domainCheckService, times(1)).checkLongLinkDomains();
    }

    @Test
    void testScheduledCheckThrowException() throws DomainsNotFoundException {
        doThrow(DomainsNotFoundException.class).when(domainCheckService).checkLongLinkDomains();
        assertThrows(RuntimeException.class, () -> longLingVerifier.scheduledCheck());
    }
}