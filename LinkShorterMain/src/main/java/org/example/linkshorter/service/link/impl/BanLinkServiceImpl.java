package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.service.exception.LinkNotFoundException;
import org.example.linkshorter.service.link.BanLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BanLinkServiceImpl implements BanLinkService {
    private final LongLinkRepository longLinkRepository;

    @Autowired
    public BanLinkServiceImpl(LongLinkRepository longLinkRepository) {
        this.longLinkRepository = longLinkRepository;
    }

    @Override
    @Transactional
    @ServiceLogging
    public void banById(Long id) {
        setBanStatus(id, true);
    }

    @Override
    @Transactional
    @ServiceLogging
    public void unbanById(Long id) {
        setBanStatus(id, false);
    }

    @Override
    @Transactional
    @ServiceLogging
    public void banByLongLink(String longLink) {
        setBanStatus(longLink, true);
    }

    @Override
    @Transactional
    @ServiceLogging
    public void unbanByLongLink(String longLink) {
        setBanStatus(longLink, false);
    }

    private void setBanStatus(Long id, boolean forbidden) {
        LongLink longLink = longLinkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException("Ссылка не найдена, id: " + id));
        longLink.setForbidden(forbidden);
        longLinkRepository.save(longLink);
    }

    private void setBanStatus(String longLink, boolean forbidden) {
        if (longLinkRepository.findByLongLink(longLink).isEmpty()) {
            return;
        }
        LongLink link = longLinkRepository.findByLongLink(longLink).get();
        link.setForbidden(forbidden);
        longLinkRepository.save(link);
    }
}
