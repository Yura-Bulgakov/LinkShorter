package org.example.linkshorter.service.control.impl;

import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.example.linkshorter.logger.ServiceLogging;
import org.example.linkshorter.repository.ClickRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.control.RedirectService;
import org.example.linkshorter.service.exception.ExpiredTokenException;
import org.example.linkshorter.service.exception.ForbiddenLinkException;
import org.example.linkshorter.service.exception.LinkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class RedirectServiceImpl implements RedirectService {
    private final ShortLinkRepository shortLinkRepository;
    private final ClickRepository clickRepository;

    @Autowired
    public RedirectServiceImpl(ShortLinkRepository shortLinkRepository, ClickRepository clickRepository) {
        this.shortLinkRepository = shortLinkRepository;
        this.clickRepository = clickRepository;
    }

    @Override
    @Transactional
    @ServiceLogging
    public String redirect(String token, HttpServletRequest request) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Токен не должен быть пустым");
        }
        ShortLink shortLink = shortLinkRepository.findByToken(token)
                .orElseThrow(() -> new LinkNotFoundException("Ссылка по токену: " + token + " не найдена"));
        User user = shortLink.getUser();
        if (user != null && user.getBanned()) {
            throw new ForbiddenLinkException(
                    String.format("Невозможен переход по токену %s, заблокированного пользователя",
                            shortLink.getToken()));
        }
        LongLink longLink = shortLink.getLongLink();
        if (longLink.isForbidden()) {
            throw new ForbiddenLinkException("Ссылка заблокирована, token: " + token);
        }
        if (shortLink.getExpirationDate() != null && shortLink.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException(String.format("Время жизни токена %s истекло %s ",
                    shortLink.getToken(), shortLink.getExpirationDate().toString()));
        }
        clickRepository.save(new Click(LocalDateTime.now(), getClientIp(request), shortLink));
        return longLink.getLongLink();
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }


}
