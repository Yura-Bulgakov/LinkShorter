package org.example.linkshorter.controller;

import org.example.linkshorter.service.control.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/r")
public class RedirectController {
    private final RedirectService redirectService;

    @Autowired
    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @RequestMapping("/{token}")
    public RedirectView redirect(@PathVariable String token, HttpServletRequest request) {
        return new RedirectView(redirectService.redirect(token, request));
    }

}
