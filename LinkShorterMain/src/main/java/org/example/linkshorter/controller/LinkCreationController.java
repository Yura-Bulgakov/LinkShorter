package org.example.linkshorter.controller;

import org.example.linkshorter.dto.TokenCreationDto;
import org.example.linkshorter.entity.ShortLink;
import org.example.linkshorter.service.link.CreationLinkService;
import org.example.linkshorter.util.LinkValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/link/creation")
public class LinkCreationController {
    private final CreationLinkService creationLinkService;
    private final LinkValidator linkValidator;

    @Autowired
    public LinkCreationController(CreationLinkService creationLinkService, LinkValidator linkValidator) {
        this.creationLinkService = creationLinkService;
        this.linkValidator = linkValidator;
    }

    @PostMapping("/json")
    public ResponseEntity<?> createToken(@Valid @RequestBody TokenCreationDto tokenCreationDto,
                                         BindingResult bindingResult) {
        linkValidator.validate(tokenCreationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        ShortLink shortLink = creationLinkService.addLink(tokenCreationDto.getUrl(), tokenCreationDto.getToken());
        return ResponseEntity.ok().body("Токен: " + shortLink.getToken() + " успешно создан");
    }

    @PostMapping("/form")
    public String createTokenWithForm(@Valid @ModelAttribute("tokenInfo") TokenCreationDto tokenCreationDto,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        linkValidator.validate(tokenCreationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/main";
        }
        creationLinkService.addLink(tokenCreationDto.getUrl(), tokenCreationDto.getToken());
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/main/page";
    }

}
