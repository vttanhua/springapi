package com.example.sa.dto.aws;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class TracingEvent extends ApplicationEvent{

	private final String uri;
	private final String username;
	private final String sessionId;
	
	public TracingEvent(Object source, String uri, String username, String sessionId) {
		super(source);
		this.uri = uri;
		this.username = username;
		this.sessionId = sessionId;
	}
}
