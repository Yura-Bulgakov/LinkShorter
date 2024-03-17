package org.example.linkshorter.util;


import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    private final UserRepository userRepository;

    public RegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDto registrationDto = (RegistrationDto) target;

        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            errors.rejectValue("username", "duplicate.username",
                    "Имя пользователя уже существует!");
        }

        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch",
                    "Пароли не совпадают");
        }
    }
}
