package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.PagingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagingUserServiceImpl implements PagingUserService {
    private final UserRepository userRepository;

    @Autowired
    public PagingUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findById(Long userId, Pageable pageable) {
        return userRepository.findById(userId, pageable);
    }

    @Override
    public Page<User> findByUsername(String username, Pageable pageable) {
        return userRepository.findByUsername(username, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
