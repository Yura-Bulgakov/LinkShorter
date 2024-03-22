package org.example.linkshorter.util;

import org.apache.commons.lang3.StringUtils;
import org.example.linkshorter.dto.TokenCreationDto;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class LinkValidator implements Validator {
    private final ShortLinkRepository shortLinkRepository;
    private final LongLinkRepository longLinkRepository;

    @Autowired
    public LinkValidator(ShortLinkRepository shortLinkRepository, LongLinkRepository longLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
        this.longLinkRepository = longLinkRepository;
    }

    public boolean isValid(String link) {
        try {
            new URL(link).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public String getDomain(String link) {
        try {
            URL url = new URL(link);
            return url.getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TokenCreationDto.class.equals(clazz);
    }

    @Override
    @ServiceLogging
    public void validate(Object target, Errors errors) {
        TokenCreationDto tokenCreationDto = (TokenCreationDto) target;
        String token = tokenCreationDto.getToken();
        String url = tokenCreationDto.getUrl();
        if (!StringUtils.isEmpty(token)) {
            if (!token.matches("[A-Za-z0-9]+")) {
                errors.rejectValue("token", "invalid.token",
                        "Токен содержит недопустимые символы! " +
                                "Допустимы латинские заглавные и прописные буквы и цифры 0-9");
            }
            if (shortLinkRepository.existsByToken(token)) {
                errors.rejectValue("token", "duplicate.token", "Токен уже существует!");
            }
        }
        if (!StringUtils.isEmpty(url) && longLinkRepository.findByLongLink(url).filter(LongLink::isForbidden).isPresent()) {
            errors.rejectValue("url", "invalid.url", "Ссылка заблокирована!");
        }
    }
}
