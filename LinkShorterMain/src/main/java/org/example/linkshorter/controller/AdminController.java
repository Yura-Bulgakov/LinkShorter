package org.example.linkshorter.controller;

import org.example.linkshorter.dto.LongLinkDto;
import org.example.linkshorter.dto.ShortLinkDto;
import org.example.linkshorter.dto.UserInfoDto;
import org.example.linkshorter.service.control.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/users")
    public String showUsers(@RequestParam(name = "userId", required = false) Long userId,
                            @RequestParam(name = "username", required = false) String username,
                            @PageableDefault(size = 20) Pageable pageable, Model model) {
        Page<UserInfoDto> users = adminService.getUsersByParameter(userId, username, pageable);
        model.addAttribute("users", users);
        return "admin/user-list";
    }


    @GetMapping("/users/token")
    public String showUsersTokens(@RequestParam(name = "userId", required = false) Long userId,
                                  @RequestParam(name = "username", required = false) String username,
                                  @RequestParam(name = "token", required = false) String token,
                                  @PageableDefault(size = 20) Pageable pageable, Model model) {
        Page<ShortLinkDto> tokens = adminService.getTokensByParameter(userId, username, token, pageable);
        model.addAttribute("tokens", tokens);
        return "admin/user-token-list";
    }

    @GetMapping("/links")
    public String showLongLinks(@RequestParam(name = "id", required = false) Long id,
                                @RequestParam(name = "token", required = false) String token,
                                @RequestParam(name = "username", required = false) String username,
                                @PageableDefault(size = 20) Pageable pageable, Model model) {
        Page<LongLinkDto> tokens = adminService.getLongLinksByParameter(id, token, username, pageable);
        model.addAttribute("links", tokens);
        return "admin/long-link-list";
    }

    @PatchMapping("users/{id}")
    public String setUserBanStatus(@PathVariable("id") Long userId, boolean banStatus) {
        adminService.changeUserBanStatus(userId, banStatus);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/token/{id}")
    public String deleteShortLink(@PathVariable("id") Long tokenId) {
        adminService.deleteShortLink(tokenId);
        return "redirect:/admin/users/token";
    }

    @PatchMapping("links/{id}")
    public String setLinkForbiddenStatus(@PathVariable("id") Long longLinkId, boolean forbiddenStatus) {
        adminService.changeLongLinkForbiddenStatus(longLinkId, forbiddenStatus);
        return "redirect:/admin/links";
    }

}
