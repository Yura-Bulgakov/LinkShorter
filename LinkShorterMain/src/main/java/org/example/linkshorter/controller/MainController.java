package org.example.linkshorter.controller;

import org.example.linkshorter.dto.TokenCreationDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("/page")
    public String showMainPage(@ModelAttribute("tokenInfo") TokenCreationDto tokenCreationDto) {
        return "main";
    }


}
