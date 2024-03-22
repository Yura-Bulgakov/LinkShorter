package org.example.linkshorter.service.control;

import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    Page<UserInfoDto> getUsersByParameter(Long userId, String username, Pageable pageable);

    Page<ShortLinkDto> getTokensByParameter(Long userId, String username, String token, Pageable pageable);

    Page<LongLinkDto> getLongLinksByParameter(Long id, String token, String username, Pageable pageable);

    void deleteShortLink(Long shortLinkId);

    void changeUserBanStatus(Long userId, boolean banStatus);

    void changeLongLinkForbiddenStatus(Long longLinkId, boolean forbiddenStatus);
}
