package org.example.linkshorter.repository;

import org.example.linkshorter.entity.LongLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LongLinkRepository extends CrudRepository<LongLink, Long> {
    Optional<LongLink> findByLongLink(String longLink);

    void deleteByLongLink(String longLink);
}
