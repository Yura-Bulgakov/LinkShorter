package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBanServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserBanServiceImpl userBanService;

    @Test
    public void testBanById() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        userBanService.banById(userId);

        verify(userRepository, times(1)).save(user);
        assertTrue(user.getBanned());
    }

    @Test
    public void testUnbanById() {
        Long userId = 1L;
        User user = new User();
        user.setBanned(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userBanService.unbanById(userId);

        verify(userRepository, times(1)).save(user);
        assertFalse(user.getBanned());
    }

    @Test
    public void testBanWithNoExistingUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userBanService.unbanById(userId);
        });
    }


}