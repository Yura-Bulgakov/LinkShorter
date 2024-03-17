package org.example.linkshorter.service.user.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.user.UpdateUserService;
import org.example.linkshorter.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserServiceImpl implements UpdateUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    @Autowired
    public UpdateUserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    @ServiceLogging
    public void update(String email, String password) {
        User user = authUtil.getUserFromAuthContext();
        if (user != null) {
            if (!StringUtils.isEmpty(email)) {
                user.setEmail(email);
            }
            if (!StringUtils.isEmpty(password)) {
                user.setPassword(passwordEncoder.encode(password));
            }
            userRepository.save(user);
        }
    }


}
