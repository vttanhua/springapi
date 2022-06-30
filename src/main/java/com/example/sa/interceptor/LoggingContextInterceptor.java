package com.example.sa.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.sa.utils.RequestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingContextInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response,
			final Object handler) {
		
		String userId = RequestUtils.getUserId();
		String sessionId = RequestUtils.getSessionId(request.getCookies());
		MDC.put("userId", userId);
		MDC.put("sessionId", sessionId);
		return true;
	}
	
	@Override
	public void afterCompletion(final HttpServletRequest request,
			final HttpServletResponse response,
			final Object handler,
			final Exception exception) {
		MDC.clear();
	}
	



	

}
