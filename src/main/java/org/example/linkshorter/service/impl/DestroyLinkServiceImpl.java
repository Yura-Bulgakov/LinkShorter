package org.example.linkshorter.service.impl;

import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.DestroyLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DestroyLinkServiceImpl implements DestroyLinkService {
    private final LongLinkRepository longLinkRepository;
    private final ShortLinkRepository shortLinkRepository;


    @Autowired
    public DestroyLinkServiceImpl(LongLinkRepository longLinkRepository, ShortLinkRepository shortLinkRepository) {
        this.longLinkRepository = longLinkRepository;
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    @Transactional
    public void deleteLink(String longLink) {
        longLinkRepository.deleteByLongLink(longLink);
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        shortLinkRepository.deleteByToken(token);
    }

}
