package org.example.linkshorter.service.link;

public interface BanLinkService {
    void banById(Long id);
    void unbanById(Long id);

    void banByLongLink(String longLink);

    void unbanByLongLink(String longLink);
}
