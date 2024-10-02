package com.makersacademy.acebook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
//	@RequestMapping(value = "/")
//	public RedirectView index() {
//		return new RedirectView("/posts");
//	}

//	@GetMapping("/templates.profile")
//	public String profile(Model model, @AuthenticationPrincipal OidcUser principal) {
//		if (principal != null) {
//			model.addAttribute("templates.profile", principal.getClaims());
//		}
//		return "templates.profile/index";
//	}
}
