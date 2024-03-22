package org.example.linkshorter.util;

import org.example.linkshorter.dto.UserEditDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

public class UserEditValidatorTest {
    private UserEditValidator userEditValidator;

    @BeforeEach
    public void setUp() {
        userEditValidator = new UserEditValidator();
    }

    @Test
    public void testValidateWithEmptyPassword() {
        UserEditDto dto = new UserEditDto();
        dto.setPassword(null);
        dto.setConfirmPassword("1");

        Errors errors = new BeanPropertyBindingResult(dto, "userEditDto");

        userEditValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Поле 'пароль' не должно быть пустым", errors.getFieldError("password").getDefaultMessage());
    }

    @Test
    public void testValidateWithEmptyConfirmPassword() {
        UserEditDto dto = new UserEditDto();
        dto.setPassword("1");
        dto.setConfirmPassword(null);

        Errors errors = new BeanPropertyBindingResult(dto, "userEditDto");

        userEditValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Поле 'подтвердите пароль' не должно быть пустым", errors.getFieldError("confirmPassword").getDefaultMessage());
    }

    @Test
    public void testValidateWithPasswordMismatch() {
        UserEditDto dto = new UserEditDto();
        dto.setPassword("1");
        dto.setConfirmPassword("2");

        Errors errors = new BeanPropertyBindingResult(dto, "userEditDto");

        userEditValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Пароли не совпадают", errors.getFieldError("confirmPassword").getDefaultMessage());
    }

    @Test
    public void testValidateWithValidData() {
        UserEditDto dto = new UserEditDto();
        dto.setPassword("1");
        dto.setConfirmPassword("1");

        Errors errors = new BeanPropertyBindingResult(dto, "userEditDto");

        userEditValidator.validate(dto, errors);

        assertEquals(0, errors.getErrorCount());
    }
}