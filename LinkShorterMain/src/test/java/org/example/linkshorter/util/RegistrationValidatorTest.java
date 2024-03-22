package org.example.linkshorter.util;

import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationValidator registrationValidator;


    @Test
    public void testValidateWithDuplicateUsername() {
        String username = "user";
        RegistrationDto dto = new RegistrationDto();
        dto.setUsername(username);
        dto.setPassword("password");
        dto.setConfirmPassword("password");

        when(userRepository.existsByUsername(username)).thenReturn(true);

        Errors errors = new BeanPropertyBindingResult(dto, "registrationDto");

        registrationValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Имя пользователя уже существует!", errors.getFieldError("username").getDefaultMessage());
    }

    @Test
    public void testValidateWithPasswordMismatch() {
        RegistrationDto dto = new RegistrationDto();
        dto.setPassword("password");
        dto.setConfirmPassword("differentPassword");

        Errors errors = new BeanPropertyBindingResult(dto, "registrationDto");

        registrationValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Пароли не совпадают", errors.getFieldError("confirmPassword").getDefaultMessage());
    }

    @Test
    public void testValidateWithValidData() {
        RegistrationDto dto = new RegistrationDto();
        dto.setUsername("newUsername");
        dto.setPassword("password");
        dto.setConfirmPassword("password");

        when(userRepository.existsByUsername("newUsername")).thenReturn(false);

        Errors errors = new BeanPropertyBindingResult(dto, "registrationDto");

        registrationValidator.validate(dto, errors);

        assertEquals(0, errors.getErrorCount());
    }

}