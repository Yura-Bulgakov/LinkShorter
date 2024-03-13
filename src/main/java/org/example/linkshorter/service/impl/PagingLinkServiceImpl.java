package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.PagingLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagingLinkServiceImpl implements PagingLinkService {

    private final ShortLinkRepository shortLinkRepository;

    @Autowired
    public PagingLinkServiceImpl(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    public Page<ShortLink> findByUserId(Long userId, Pageable pageable) {
        return shortLinkRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<ShortLink> findByUsername(String username, Pageable pageable) {
        return shortLinkRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Page<ShortLink> findByToken(String token, Pageable pageable) {
        return shortLinkRepository.findByToken(token, pageable);
    }

    @Override
    public Page<ShortLink> findAll(Pageable pageable) {
        return shortLinkRepository.findAll(pageable);
    }
}
