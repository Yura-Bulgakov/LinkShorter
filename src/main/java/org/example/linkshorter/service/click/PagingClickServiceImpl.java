package org.example.linkshorter.service.click;

import org.example.linkshorter.entity.Click;
import org.example.linkshorter.repository.ClickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagingClickServiceImpl implements PagingClickService {

    private final ClickRepository clickRepository;

    @Autowired
    public PagingClickServiceImpl(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Override
    public Page<Click> findByTokenId(Long tokenId, Pageable pageable) {
        return clickRepository.findByShortLinkId(tokenId, pageable);
    }

}
