package org.example.linkshorter.legalization;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.service.link.BanLinkService;
import org.example.linkshorter.util.LinkValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RknDomainCheckService implements DomainCheckService {
    private final LongLinkRepository longLinkRepository;
    private final LinkValidator linkValidator;
    private final BanLinkService banLinkService;
    private final RknDomainLoader rknDomainLoader;

    public RknDomainCheckService(LongLinkRepository longLinkRepository, LinkValidator linkValidator, BanLinkService banLinkService, RknDomainLoader rknDomainLoader) {
        this.longLinkRepository = longLinkRepository;
        this.linkValidator = linkValidator;
        this.banLinkService = banLinkService;
        this.rknDomainLoader = rknDomainLoader;
    }

    @Override
    @ServiceLogging
    public void checkLongLinkDomains() throws DomainsNotFoundException {
        List<String> blockedDomains = rknDomainLoader.loadDomains();
        List<LongLink> longLinks = longLinkRepository.findAllByForbiddenFalse();
        longLinks.parallelStream()
                .map(LongLink::getLongLink)
                .forEach(x ->{
                    String domain = linkValidator.getDomain(x);
                    if (blockedDomains.contains(domain)){
                        banLinkService.banByLongLink(x);
                    }
                });
    }

}
