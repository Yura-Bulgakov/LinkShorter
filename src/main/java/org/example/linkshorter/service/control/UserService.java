package org.example.linkshorter.service.control;

import org.example.linkshorter.dto.ClickDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserEditDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserInfoDto getUserInfo();

    void updateUser(UserEditDto updateInfo);

    Page<ShortLinkDto> getUserTokens(Pageable pageable);

    Page<ClickDto> getUserTokenClicks(Long tokenId, Pageable pageable);

    void deleteUserToken(Long tokenId);
}
