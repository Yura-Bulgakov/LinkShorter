package org.example.linkshorter.service.control;

import org.example.linkshorter.dto.RegistrationDto;

public interface AuthService {
    void register(RegistrationDto registrationInfo);
}
