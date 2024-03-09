package org.example.linkshorter.service;

import javax.servlet.http.HttpServletRequest;

public interface RedirectService {
    String redirect(String token, HttpServletRequest request);
}
