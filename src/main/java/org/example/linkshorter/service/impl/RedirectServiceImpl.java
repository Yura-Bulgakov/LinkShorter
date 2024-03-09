package org.example.linkshorter.service.impl;

import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.repository.ClickRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.example.linkshorter.service.RedirectService;
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
    public String redirect(String token, HttpServletRequest request) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Токен не должен быть пустым");
        }
        ShortLink shortLink = shortLinkRepository.findByToken(token)
                .orElseThrow(() -> new LinkNotFoundException("Ссылка по токену не найдена"));
        LongLink longLink = shortLink.getLongLink();
        clickRepository.save(new Click(LocalDateTime.now(), getClientIp(request), shortLink));
        return "redirect:" + longLink.getLongLink();
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }


}
