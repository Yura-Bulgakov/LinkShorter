package org.example.linkshorter.service.user.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.exception.UserNotFoundException;
import org.example.linkshorter.service.user.UserBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBanServiceImpl implements UserBanService {
    private final UserRepository userRepository;

    @Autowired
    public UserBanServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void banById(Long id) {
        setBanStatus(id, true);
    }

    @Override
    @Transactional
    public void unbanById(Long id) {
        setBanStatus(id, false);
    }

    private void setBanStatus(Long id, boolean banned) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден, id: " + id));
        user.setBanned(banned);
        userRepository.save(user);
    }

}
