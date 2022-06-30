package com.example.sa.controller.thymeleaf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sa.controller.ExchangeRateController;
import com.example.sa.dto.aws.TracingEvent;
import com.example.sa.utils.RequestUtils;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public IndexController(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	@GetMapping
	public String getIndex(Model model, @AuthenticationPrincipal OidcUser user, HttpServletRequest request, 
	        HttpServletResponse response) {
		log.info("Entering IndexController*******");
		String sessionId = RequestUtils.getSessionId(request.getCookies());
		if(user != null) {
		OidcUser user2 = (OidcUser) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		this.eventPublisher.publishEvent(new TracingEvent(this, "index", RequestUtils.getUserId(), sessionId));
		log.info("IndexController: user data is {}",user2);
		}
		else {
			this.eventPublisher.publishEvent(new TracingEvent(this, "index", RequestUtils.getUserId(), sessionId));
		}
		return "index";
	}

}
