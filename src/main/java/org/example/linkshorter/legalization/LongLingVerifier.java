package org.example.linkshorter.legalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LongLingVerifier {
    private final DomainCheckService domainCheckService;

    @Autowired
    public LongLingVerifier(DomainCheckService domainCheckService) {
        this.domainCheckService = domainCheckService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledCheck() {
        try {
            domainCheckService.checkLongLinkDomains();
        } catch (DomainsNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
