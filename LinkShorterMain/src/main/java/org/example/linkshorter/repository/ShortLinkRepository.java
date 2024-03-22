package org.example.linkshorter.repository;

import org.example.linkshorter.entity.ShortLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortLinkRepository extends PagingAndSortingRepository<ShortLink, Long> {
    Optional<ShortLink> findByToken(String token);

    boolean existsByToken(String token);

    void deleteByToken(String token);

    Page<ShortLink> findByUserId(Long userId, Pageable pageable);

    Page<ShortLink> findByUserUsername(String username, Pageable pageable);

}
