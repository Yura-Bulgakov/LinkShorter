package org.example.linkshorter.repository;

import org.example.linkshorter.entity.ShortLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {
    Optional<ShortLink> findByToken(String token);
}
