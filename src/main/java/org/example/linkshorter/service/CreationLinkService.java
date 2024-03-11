package org.example.linkshorter.service;

public interface CreationLinkService {
    void addLink(String longLink);

    void addLink(String longLink, String token);
}
