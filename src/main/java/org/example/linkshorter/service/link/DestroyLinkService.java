package org.example.linkshorter.service.link;

public interface DestroyLinkService {
    void deleteLink(String longLink);

    void deleteToken(String token);

    void deleteLink(Long longLinkId);

    void deleteToken(Long tokenId);
}
