package org.example.linkshorter.service.user.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.user.PagingUserService;
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
    @ServiceLogging
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    @ServiceLogging
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @ServiceLogging
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
