package org.example.linkshorter.service;

public interface DestroyLinkService {
    void deleteLink(String longLink);

    void deleteToken(String token);
}
