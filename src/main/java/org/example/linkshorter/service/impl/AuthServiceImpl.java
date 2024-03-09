package org.example.linkshorter.service.impl;

import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.entity.UserRole;
import org.example.linkshorter.repository.RoleRepository;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegistrationDto registrationInfo) {
        User newUser = new User();
        newUser.setId(null);
        newUser.setUsername(registrationInfo.getUsername());
        newUser.setEmail(registrationInfo.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationInfo.getPassword()));
        newUser.setRole(roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Не удалось установить роль")));
        userRepository.save(newUser);
    }


}
