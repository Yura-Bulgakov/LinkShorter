package org.example.linkshorter.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.UpdateUserService;
import org.example.linkshorter.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserServiceImpl implements UpdateUserService {
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Autowired
    public UpdateUserServiceImpl(UserRepository userRepository, AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }

    @Override
    @Transactional
    public void update(String email, String password) {
        User user = authUtil.getUserFromAuthContext();
        if (user != null) {
            if (!StringUtils.isEmpty(email)) {
                user.setEmail(email);
            }
            if (!StringUtils.isEmpty(password)) {
                user.setPassword(password);
            }
            userRepository.save(user);
        }
    }


}
