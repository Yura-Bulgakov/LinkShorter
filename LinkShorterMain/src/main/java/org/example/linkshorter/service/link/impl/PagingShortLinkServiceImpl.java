package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.link.PagingShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagingShortLinkServiceImpl implements PagingShortLinkService {

    private final ShortLinkRepository shortLinkRepository;

    @Autowired
    public PagingShortLinkServiceImpl(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    @ServiceLogging
    public Page<ShortLink> findByUserId(Long userId, Pageable pageable) {
        return shortLinkRepository.findByUserId(userId, pageable);
    }

    @Override
    @ServiceLogging
    public Page<ShortLink> findByUsername(String username, Pageable pageable) {
        return shortLinkRepository.findByUserUsername(username, pageable);
    }

    @Override
    @ServiceLogging
    public ShortLink findByToken(String token) {
        return shortLinkRepository.findByToken(token).orElse(null);
    }

    @Override
    @ServiceLogging
    public Page<ShortLink> findAll(Pageable pageable) {
        return shortLinkRepository.findAll(pageable);
    }
}
