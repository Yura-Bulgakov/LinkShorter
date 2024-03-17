package org.example.linkshorter.service.link.impl;

import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.service.link.PagingLongLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagingLongLinkServiceImpl implements PagingLongLinkService {
    private final LongLinkRepository longLinkRepository;

    @Autowired
    public PagingLongLinkServiceImpl(LongLinkRepository longLinkRepository) {
        this.longLinkRepository = longLinkRepository;
    }

    @Override
    public Page<LongLink> findAll(Pageable pageable) {
        return longLinkRepository.findAll(pageable);
    }

    @Override
    public LongLink findById(Long id) {
        return longLinkRepository.findById(id).orElse(null);
    }

    @Override
    public Page<LongLink> findAllByUsername(String username, Pageable pageable) {
        return longLinkRepository.findAllByUsername(username, pageable);
    }

    @Override
    public LongLink findByToken(String token) {
        return longLinkRepository.findByToken(token);
    }
}
