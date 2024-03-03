package org.example.linkshorter.repository;

import org.example.linkshorter.entity.ShortLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {
}
