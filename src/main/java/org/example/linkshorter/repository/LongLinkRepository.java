package org.example.linkshorter.repository;

import org.example.linkshorter.entity.LongLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongLinkRepository extends CrudRepository<LongLink, Long> {
}
