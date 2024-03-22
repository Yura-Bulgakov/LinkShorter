package org.example.linkshorter.mapping;

import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInfoDto userToUserInfoDto(User user) {
        if (user == null) {
            return new UserInfoDto();
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setTokenCounts(user.getShortLinks().size());
        userInfoDto.setRedirectCounts(user.getShortLinks().stream()
                .mapToLong(x -> x.getClicks().size())
                .reduce(0L, Long::sum));
        userInfoDto.setBanned(user.getBanned());
        return userInfoDto;
    }

}
