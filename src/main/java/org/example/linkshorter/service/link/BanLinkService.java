package org.example.linkshorter.service.link;

public interface BanLinkService {
    void banById(Long id);

    void unbanById(Long id);
}
