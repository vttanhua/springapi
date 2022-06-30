package com.example.sa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


@Configuration
@Profile("!dev")
public class AwsConfig {

	@Bean
	@ConditionalOnProperty(prefix = "custom", name = "use-cognito-as-identity-provider", havingValue = "true")
	public AWSCognitoIdentityProvider awsCognitoIdentityProvider(
			@Value("${cloud.aws.region.static}") String region,
			AWSCredentialsProvider awsCredentialsProvider) {
		return AWSCognitoIdentityProviderAsyncClientBuilder.standard()
				.withCredentials(awsCredentialsProvider)
				.withRegion(region)
				.build();
	}
	
	
	@Bean
	public AmazonDynamoDB amazonDynamoDB(
		@Value("${cloud.aws.region.static}") String region,
		AWSCredentialsProvider awsCredentialsProvider) {
		return AmazonDynamoDBClientBuilder.standard()
				.withCredentials(awsCredentialsProvider)
				.withRegion(region)
				.build();
	}

}
