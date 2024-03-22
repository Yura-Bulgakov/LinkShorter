package org.example.linkshorter.controller;

import org.example.linkshorter.dto.UserEditDto;
import org.example.linkshorter.service.control.UserService;
import org.example.linkshorter.util.UserEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserEditValidator userEditValidator;

    @Autowired
    public UserController(UserService userService, UserEditValidator userEditValidator) {
        this.userService = userService;
        this.userEditValidator = userEditValidator;
    }

    @GetMapping("/info")
    public String showUserInfo(Model model) {
        model.addAttribute("user", userService.getUserInfo());
        return "user/user-info";
    }

    @GetMapping("/edit")
    public String showEditForm(@ModelAttribute("userInfo") UserEditDto userEditInfo) {
        return "user/user-edit";
    }

    @PatchMapping
    public String editUser(@Valid @ModelAttribute("userInfo") UserEditDto userEditInfo, BindingResult bindingResult) {
        userEditValidator.validate(userEditInfo, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/user-edit";
        }
        userService.updateUser(userEditInfo);
        return "redirect:/user/info";
    }

    @GetMapping("/tokens")
    public String showUserTokens(@PageableDefault(size = 20) Pageable pageable, Model model) {
        model.addAttribute("tokens", userService.getUserTokens(pageable));
        return "user/user-tokens";
    }

    @GetMapping("/tokens/{token-id}")
    public String showUserTokenStatistic(@PathVariable("token-id") Long tokenId,
                                         @PageableDefault(size = 20) Pageable pageable, Model model) {
        model.addAttribute("clicks", userService.getUserTokenClicks(tokenId, pageable));
        model.addAttribute("tokenId", tokenId);
        return "user/user-token-clicks";
    }

    @DeleteMapping("/tokens/{token-id}")
    public String deleteUserToken(@PathVariable("token-id") Long tokenId) {
        userService.deleteUserToken(tokenId);
        return "redirect:/user/tokens";
    }

}
