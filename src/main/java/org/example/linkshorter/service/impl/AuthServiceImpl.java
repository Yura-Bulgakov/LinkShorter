package org.example.linkshorter.service.impl;

import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.entity.UserRole;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(RegistrationDto registrationInfo) {
        User newUser = new User();
        newUser.setId(null);
        newUser.setRole(UserRole.ROLE_USER);
        newUser.setUsername(registrationInfo.getUsername());
        newUser.setEmail(registrationInfo.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationInfo.getPassword()));
        userRepository.save(newUser);
    }


}
