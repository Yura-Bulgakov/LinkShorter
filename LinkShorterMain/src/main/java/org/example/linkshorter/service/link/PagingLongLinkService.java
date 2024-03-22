package org.example.linkshorter.service.link;

import org.example.linkshorter.entity.LongLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingLongLinkService {
    Page<LongLink> findAll(Pageable pageable);

    LongLink findById(Long id);

    Page<LongLink> findAllByUsername(String username, Pageable pageable);

    LongLink findByToken(String token);
}
