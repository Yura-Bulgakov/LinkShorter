package org.example.linkshorter.service;

import org.example.linkshorter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingUserService {
    Page<User> findById(Long userId, Pageable pageable);

    Page<User> findByUsername(String username, Pageable pageable);

    Page<User> findAll(Pageable pageable);
}
