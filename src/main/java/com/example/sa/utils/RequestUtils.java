package com.example.sa.utils;

import javax.servlet.http.Cookie;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestUtils {

	public static String getSessionId(Cookie[] cookies) {
		for(Cookie cookie : cookies){
			if(cookie.getName().equalsIgnoreCase("JSESSIONID"))
				return cookie.getValue();
		}
		return "noSession";
	}
	
	private static String getUserIdFromPrincipal(Object principal) {
		if(principal instanceof String)	
			return principal.toString();
		if(principal instanceof OidcUser) {
			try {
				OidcUser user = (OidcUser) principal;
				if(user.getPreferredUsername() != null) {
					return user.getPreferredUsername();
				}
				else if(user.getClaimAsString("name") != null) {
					return user.getClaimAsString("name");
				}
				else if(user.getClaimAsString("username") != null) {
					return user.getClaimAsString("username");
				}
				else {
					log.warn("Could not extract userId from Principal***");
					return "unknown";
				}
			}catch(Exception e) {
				log.warn("Could not extract userid from Principal. Got exception: ", e);
			}
		}
		return "unknown";
	}

	public static String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getUserIdFromPrincipal(authentication.getPrincipal());

	}
}
