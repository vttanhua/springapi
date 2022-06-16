package com.example.sa.service.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.example.sa.dto.aws.Registration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
@ConditionalOnProperty(prefix="custom", name="use-cognito-as-identity-provider", havingValue="true")
public class CognitoRegistrationService implements RegistrationService{

	private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
	private final MeterRegistry meterRegistry;
	private final String userPoolId;
	
	
	@Autowired
	public CognitoRegistrationService(@Value("${COGNITO_USER_POOL_ID}") String userPoolId,
			AWSCognitoIdentityProvider awsCognitoIdentityProvider,
			MeterRegistry meterRegistry
			) {
		this.awsCognitoIdentityProvider = awsCognitoIdentityProvider;
		this.meterRegistry = meterRegistry;
		this.userPoolId = userPoolId;
	}
	
	@Override
	public void registerUser(Registration registration) {
		AdminCreateUserRequest registrationRequest = new AdminCreateUserRequest()
				.withUserPoolId(userPoolId)
				.withUsername(registration.getUsername())
				.withUserAttributes(
						new AttributeType().withName("email").withValue(registration.getEmail()),
						new AttributeType().withName("name").withValue(registration.getUsername()),
						new AttributeType().withName("email_verified").withValue("true")
						)
				.withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
				.withForceAliasCreation(Boolean.FALSE);
		
		awsCognitoIdentityProvider.adminCreateUser(registrationRequest);
		
		Counter successCounter = Counter.builder("vttanhua.springapi.registration.signups")
				.description("Number of user registrations")
				.tag("outcome", "success")
				.register(meterRegistry);
		successCounter.increment();
	}
	


}
