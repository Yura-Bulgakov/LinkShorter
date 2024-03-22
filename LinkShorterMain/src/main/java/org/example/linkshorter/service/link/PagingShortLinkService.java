package org.example.linkshorter.service.link;

import org.example.linkshorter.entity.ShortLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingShortLinkService {
    Page<ShortLink> findByUserId(Long userId, Pageable pageable);

    Page<ShortLink> findByUsername(String username, Pageable pageable);

    ShortLink findByToken(String token);

    Page<ShortLink> findAll(Pageable pageable);
}
