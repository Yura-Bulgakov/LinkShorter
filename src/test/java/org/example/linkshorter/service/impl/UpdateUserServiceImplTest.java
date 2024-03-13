package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.User;
import org.example.linkshorter.repository.UserRepository;
import org.example.linkshorter.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthUtil authUtil;

    @InjectMocks
    private UpdateUserServiceImpl updateUserService;

    @Test
    public void testUpdateWithEmailAndPassword() {
        String email = "test@mail.ru";
        String password = "pas";

        User mockUser = new User();
        when(authUtil.getUserFromAuthContext()).thenReturn(mockUser);

        updateUserService.update(email, password);

        verify(userRepository, times(1)).save(mockUser);
        assertEquals(email, mockUser.getEmail());
        assertEquals(password, mockUser.getPassword());
    }

    @Test
    public void testUpdateWithEmail() {
        String email = "test@mail.ru";
        String newPassword = null;

        User mockUser = mock(User.class);
        when(authUtil.getUserFromAuthContext()).thenReturn(mockUser);

        updateUserService.update(email, newPassword);

        verify(userRepository, times(1)).save(mockUser);
        verify(mockUser, never()).setPassword(anyString());
    }

    @Test
    public void testUpdateWithPassword() {
        String email = null;
        String password = "newPassword";

        User mockUser = mock(User.class);
        when(authUtil.getUserFromAuthContext()).thenReturn(mockUser);

        updateUserService.update(email, password);

        verify(userRepository, times(1)).save(mockUser);
        verify(mockUser, times(1)).setPassword(password);
        verify(mockUser, never()).setEmail(anyString());
    }

    @Test
    public void testUpdateWithoutEmailAndPassword() {
        String email = "";
        String password = "";

        User mockUser = mock(User.class);
        when(authUtil.getUserFromAuthContext()).thenReturn(mockUser);

        updateUserService.update(email, password);

        verify(userRepository, times(1)).save(mockUser);
        verify(mockUser, never()).setPassword(anyString());
        verify(mockUser, never()).setEmail(anyString());

    }

    @Test
    public void testUpdateWithNoCurrentUser() {
        String email = "test@mail.ru";
        String password = "pas";

        when(authUtil.getUserFromAuthContext()).thenReturn(null);

        updateUserService.update(email, password);

        verify(userRepository, never()).save(any());
    }

}