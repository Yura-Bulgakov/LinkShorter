package org.example.linkshorter.service;

import org.example.linkshorter.entity.ShortLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingLinkService {
    Page<ShortLink> findByUserId(Long userId, Pageable pageable);

    Page<ShortLink> findByUsername(String username, Pageable pageable);

    Page<ShortLink> findByToken(String token, Pageable pageable);

    Page<ShortLink> findAll(Pageable pageable);
}
