package org.example.linkshorter.repository;

import org.example.linkshorter.entity.Click;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository extends PagingAndSortingRepository<Click, Long> {
    Page<Click> findByShortLinkId(Long shortLinkId, Pageable pageable);
}
