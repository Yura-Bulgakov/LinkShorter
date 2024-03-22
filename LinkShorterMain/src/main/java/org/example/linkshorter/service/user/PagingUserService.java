package org.example.linkshorter.service.user;

import org.example.linkshorter.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingUserService {
    User findById(Long userId);

    User findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
