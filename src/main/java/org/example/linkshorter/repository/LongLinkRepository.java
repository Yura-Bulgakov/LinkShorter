package org.example.linkshorter.repository;

import org.example.linkshorter.entity.LongLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LongLinkRepository extends PagingAndSortingRepository<LongLink, Long> {
    Optional<LongLink> findByLongLink(String longLink);

    void deleteByLongLink(String longLink);

    @Query("SELECT l FROM LongLink l JOIN l.shortLinks sl JOIN sl.user u WHERE u.username = :username")
    Page<LongLink> findAllByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT l FROM LongLink l JOIN l.shortLinks sl WHERE sl.token = :token")
    LongLink findByToken(@Param("token") String token);
}
