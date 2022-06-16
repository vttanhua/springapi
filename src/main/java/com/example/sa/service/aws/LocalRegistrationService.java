package com.example.sa.service.aws;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.sa.dto.aws.Registration;

import groovy.util.logging.Slf4j;

@Slf4j
@Service
@ConditionalOnProperty(prefix="custom", name="use-cognito-as-identity-provider", havingValue="false")
public class LocalRegistrationService implements RegistrationService{

	@Override
	public void registerUser(Registration registration) {
		// TODO Auto-generated method stub
		
	}

}
