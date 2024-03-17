package org.example.linkshorter.util;

import org.example.linkshorter.dto.UserEditDto;
import org.example.linkshorter.logger.ServiceLogging;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEditValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserEditDto.class.equals(clazz);
    }

    @Override
    @ServiceLogging
    public void validate(Object target, Errors errors) {
        UserEditDto userEditDto = (UserEditDto) target;
        if (userEditDto.getPassword() != null && userEditDto.getConfirmPassword() == null) {
            errors.rejectValue("confirmPassword", "password.mismatch",
                    "Поле 'подтвердите пароль' не должно быть пустым");
        } else if (userEditDto.getConfirmPassword() != null && userEditDto.getPassword() == null) {
            errors.rejectValue("password", "password.mismatch",
                    "Поле 'пароль' не должно быть пустым");
        }

        if (userEditDto.getPassword() != null &&
                !userEditDto.getPassword().equals(userEditDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch",
                    "Пароли не совпадают");
        }

    }
}
