package org.example.linkshorter.service;

public interface UserBanService {
    void banById(Long id);

    void unbanById(Long id);
}
