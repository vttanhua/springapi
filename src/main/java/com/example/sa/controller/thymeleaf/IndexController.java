package com.example.sa.controller.thymeleaf;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sa.controller.ExchangeRateController;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	@GetMapping
	public String getIndex(Model model, @AuthenticationPrincipal OidcUser user) {
		log.info("Entering IndexController*******");
		if(user != null) {
		OidcUser user2 = (OidcUser) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		log.info("IndexController: user data is {}",user2);
		}
		return "index";
	}

}
