package org.example.linkshorter.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TokenCreationDto {

    @NotEmpty(message = "Поле ссылка не должно быть пустым")
    @Size(max = 2000, message = "Размер ссылки не должен превышать 2000 символов")
    @URL(message = "Не валидная ссылка")
    private String url;

    private String token;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
