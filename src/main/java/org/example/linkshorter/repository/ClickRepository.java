package org.example.linkshorter.repository;

import org.example.linkshorter.entity.Click;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository extends CrudRepository<Click, Long> {
}
