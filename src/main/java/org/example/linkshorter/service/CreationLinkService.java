package org.example.linkshorter.service;

public interface CreationLinkService {
    void addLink(String longLink);

    void addToken(String longLink, String token);
}
