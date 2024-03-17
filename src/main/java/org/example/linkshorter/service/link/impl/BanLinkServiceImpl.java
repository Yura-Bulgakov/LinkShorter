package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.LongLink;
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
    public void banById(Long id) {
        setBanStatus(id, true);
    }

    @Override
    @Transactional
    public void unbanById(Long id) {
        setBanStatus(id, false);
    }

    private void setBanStatus(Long id, boolean forbidden) {
        LongLink longLink = longLinkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException("Ссылка не найдена, id: " + id));
        longLink.setForbidden(forbidden);
        longLinkRepository.save(longLink);
    }
}
