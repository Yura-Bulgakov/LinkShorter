package org.example.linkshorter.repository;

import org.example.linkshorter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Page<User> findById(Long userId, Pageable pageable);

    Page<User> findByUsername(String username, Pageable pageable);
}
