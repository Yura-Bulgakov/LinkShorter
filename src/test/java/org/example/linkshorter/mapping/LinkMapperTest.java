package org.example.linkshorter.mapping;

import org.example.linkshorter.dto.ClickDto;
import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.entity.Click;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LinkMapperTest {
    @Test
    void testShortLinkToShortLinkDtoWithNullShortLink() {
        LinkMapper linkMapper = new LinkMapper();
        ShortLinkDto shortLinkDto = linkMapper.shortLinkToShortLinkDto(null);
        assertNotNull(shortLinkDto);
        assertNull(shortLinkDto.getTokenId());
        assertNull(shortLinkDto.getToken());
        assertNull(shortLinkDto.getUrl());
        assertEquals(0, shortLinkDto.getClicks());
        assertNull(shortLinkDto.getUserId());
        assertNull(shortLinkDto.getUsername());
    }

    @Test
    void testShortLinkToShortLinkDtoWithNotNullShortLink() {
        LinkMapper linkMapper = new LinkMapper();
        ShortLink shortLink = new ShortLink();
        shortLink.setId(1L);
        shortLink.setToken("token");
        LongLink longLink = new LongLink();
        longLink.setLongLink("long link");
        shortLink.setLongLink(longLink);
        User user = new User();
        user.setUsername("user");
        user.setId(1L);
        shortLink.setUser(user);
        shortLink.setClicks(new HashSet<>());

        ShortLinkDto shortLinkDto = linkMapper.shortLinkToShortLinkDto(shortLink);

        assertNotNull(shortLinkDto);
        assertEquals(1L, shortLinkDto.getTokenId());
        assertEquals("token", shortLinkDto.getToken());
        assertEquals("long link", shortLinkDto.getUrl());
        assertEquals(0, shortLinkDto.getClicks());
        assertEquals(1L, shortLinkDto.getUserId());
        assertEquals("user", shortLinkDto.getUsername());
    }

    @Test
    void testClickToClickDtoWithNullClick() {
        LinkMapper linkMapper = new LinkMapper();
        ClickDto clickDto = linkMapper.clickTpClickDto(null);
        assertNotNull(clickDto);
        assertNull(clickDto.getUrl());
        assertNull(clickDto.getToken());
        assertNull(clickDto.getClickDate());
        assertNull(clickDto.getClientIp());
    }

    @Test
    void testClickToClickDtoWithNotNullClick() {
        LinkMapper linkMapper = new LinkMapper();
        Click click = new Click();
        click.setClientIp("127.0.0.1");
        click.setClickDate(LocalDateTime.now());
        ShortLink shortLink = new ShortLink();
        LongLink longLink = new LongLink();
        longLink.setLongLink("long link");
        shortLink.setLongLink(longLink);
        click.setShortLink(shortLink);

        ClickDto clickDto = linkMapper.clickTpClickDto(click);

        assertNotNull(clickDto);
        assertEquals("long link", clickDto.getUrl());
        assertNull(clickDto.getToken());
        assertNotNull(clickDto.getClickDate());
        assertEquals("127.0.0.1", clickDto.getClientIp());
    }

    @Test
    void testLongLinkToLongLinkDtoWithNullLongLink() {
        LinkMapper linkMapper = new LinkMapper();
        LongLinkDto longLinkDto = linkMapper.longLinkToLongLinkDto(null);
        assertNotNull(longLinkDto);
        assertNull(longLinkDto.getId());
        assertNull(longLinkDto.getUrl());
        assertFalse(longLinkDto.isForbidden());
    }

    @Test
    void testLongLinkToLongLinkDtoWithNotNullLongLink() {
        LinkMapper linkMapper = new LinkMapper();
        LongLink longLink = new LongLink();
        longLink.setId(1L);
        longLink.setLongLink("long link");
        longLink.setForbidden(true);

        LongLinkDto longLinkDto = linkMapper.longLinkToLongLinkDto(longLink);

        assertNotNull(longLinkDto);
        assertEquals(1L, longLinkDto.getId());
        assertEquals("long link", longLinkDto.getUrl());
        assertTrue(longLinkDto.isForbidden());
    }

}