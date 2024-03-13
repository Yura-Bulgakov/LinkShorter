package org.example.linkshorter.service;

import org.example.linkshorter.entity.Click;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingClickService {
    Page<Click> findByTokenId(Long tokenId, Pageable pageable);
}
