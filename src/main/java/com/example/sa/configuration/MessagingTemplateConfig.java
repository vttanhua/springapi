package com.example.sa.configuration;

import java.util.Collections;

import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

@Slf4j
@Configuration
public class MessagingTemplateConfig {

	private final ObjectMapper objectMapper;
	
	public MessagingTemplateConfig(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
		log.info("amazonSQSAsync is: {}******************",amazonSQSAsync);
		return new QueueMessagingTemplate(amazonSQSAsync);
	}
	
	@Bean
	public QueueMessageHandlerFactory queueMessageHandlerFactory() {
		QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
		messageConverter.setObjectMapper(objectMapper);
		messageConverter.setStrictContentTypeMatch(false);
		factory.setArgumentResolvers(Collections.singletonList(new PayloadMethodArgumentResolver(messageConverter)));
		return factory;
	}
}
