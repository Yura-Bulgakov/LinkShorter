package org.example.linkshorter.service.user;

public interface UserBanService {
    void banById(Long id);

    void unbanById(Long id);
}
