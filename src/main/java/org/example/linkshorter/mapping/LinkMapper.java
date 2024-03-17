package org.example.linkshorter.mapping;

import org.example.linkshorter.dto.ClickDto;
import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper {

    public ShortLinkDto shortLinkToShortLinkDto(ShortLink shortLink) {
        if (shortLink == null) {
            return new ShortLinkDto();
        }
        ShortLinkDto shortLinkDto = new ShortLinkDto();
        shortLinkDto.setTokenId(shortLink.getId());
        shortLinkDto.setToken(shortLink.getToken());
        shortLinkDto.setUrl(shortLink.getLongLink().getLongLink());
        shortLinkDto.setClicks(shortLink.getClicks().size());
        if (shortLink.getUser() != null) {
            shortLinkDto.setUserId(shortLink.getUser().getId());
            shortLinkDto.setUsername(shortLink.getUser().getUsername());
        }
        return shortLinkDto;
    }

    public ClickDto clickTpClickDto(Click click) {
        if (click == null) {
            return new ClickDto();
        }
        ClickDto clickDto = new ClickDto();
        clickDto.setUrl(click.getShortLink().getLongLink().getLongLink());
        clickDto.setToken(click.getShortLink().getToken());
        clickDto.setClickDate(click.getClickDate());
        clickDto.setClientIp(click.getClientIp());
        return clickDto;
    }

    public LongLinkDto longLinkToLongLinkDto(LongLink longLink) {
        if (longLink == null) {
            return new LongLinkDto();
        }
        LongLinkDto longLinkDto = new LongLinkDto();
        longLinkDto.setId(longLink.getId());
        longLinkDto.setUrl(longLink.getLongLink());
        longLinkDto.setForbidden(longLink.isForbidden());
        return longLinkDto;
    }
}
