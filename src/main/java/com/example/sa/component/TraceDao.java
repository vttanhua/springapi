package com.example.sa.component;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.sa.dto.aws.TracingEvent;
import com.example.sa.entity.aws.Breadcrumb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TraceDao {
	
	private final DynamoDBMapper dynamoDBMapper;
	
	@Autowired
	public TraceDao(DynamoDBMapper dynamoDBMapper) {
		this.dynamoDBMapper = dynamoDBMapper;
	}
	
	@EventListener(TracingEvent.class)
	public Breadcrumb create(TracingEvent tracingEvent) {
		Breadcrumb breadcrumb = new Breadcrumb();
	    breadcrumb.setUri(tracingEvent.getUri());
	    breadcrumb.setUsername(tracingEvent.getUsername());
	    breadcrumb.setTimestamp(ZonedDateTime.now().toString());
	    breadcrumb.setSessionId(tracingEvent.getSessionId());
	    log.info("Saving trace {}",breadcrumb);
	    dynamoDBMapper.save(breadcrumb);
		return breadcrumb;
	}
}
