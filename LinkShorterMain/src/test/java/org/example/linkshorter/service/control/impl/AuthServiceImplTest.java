package org.example.linkshorter.service.control.impl;

import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterValidUser() {
        RegistrationDto registrationDto = new RegistrationDto("testUser", "test@mail.com",
                "password", "password");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        authService.register(registrationDto);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }
}