package com.alvindo.spring_blogs_api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
    @GetMapping
    public String home(Model model, @AuthenticationPrincipal OidcUser principal){
        if (model != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }
}
