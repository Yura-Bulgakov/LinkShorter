package org.example.linkshorter.mapping;

import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.entity.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    void testUserToUserInfoDtoWithNullUser() {
        UserMapper userMapper = new UserMapper();
        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(null);
        assertNotNull(userInfoDto);
    }

    @Test
    void testUserToUserInfoDtoWithNotNullUser() {
        UserMapper userMapper = new UserMapper();
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setBanned(true);
        Set<ShortLink> shortLinks = new HashSet<>();
        shortLinks.add(new ShortLink());
        shortLinks.add(new ShortLink());
        user.setShortLinks(shortLinks);

        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(user);

        assertNotNull(userInfoDto);
        assertEquals(1L, userInfoDto.getUserId());
        assertEquals("testUser", userInfoDto.getUsername());
        assertEquals("test@test.com", userInfoDto.getEmail());
        assertEquals(2, userInfoDto.getTokenCounts());
        assertEquals(0, userInfoDto.getRedirectCounts());
        assertTrue(userInfoDto.isBanned());
    }

}