package org.example.linkshorter.service.user.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagingUserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    PagingUserServiceImpl pagingUserService;

    @Test
    public void testFindById() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = pagingUserService.findById(userId);

        assertEquals(user, result);
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        User user = new User();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = pagingUserService.findByUsername(username);

        assertEquals(user, result);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = Pageable.unpaged();
        List<User> users = Arrays.asList(new User(), new User());

        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users));

        Page<User> result = pagingUserService.findAll(pageable);

        assertEquals(users.size(), result.getContent().size());
    }
}