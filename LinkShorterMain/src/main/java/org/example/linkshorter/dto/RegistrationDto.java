package org.example.linkshorter.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegistrationDto {
    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    private String username;
    @Email(message = "Некорректный email")
    @NotEmpty(message = "Поле email не должно быть пустым")
    private String email;
    @NotEmpty(message = "Поле пароль не должно быть пустым")
    private String password;
    @NotEmpty(message = "Поле подтверждение пароля не должно быть пустым")
    private String confirmPassword;

    public RegistrationDto(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public RegistrationDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
