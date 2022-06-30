package com.example.sa.configuration;

import javax.annotation.Priority;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
@Profile("dev")
public class AmazonLocalConfig {
	
	@Value("${cloud.aws.sqs.endpoint.uri}")
	private String endpointUri;
	
	
	@Value("${cloud.aws.sqs.region}")
	private String sqsRegion;
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String awsAccessKey;
	
	@Value("${cloud.aws.credentials.secretKey}")
	private String awsSecretKey;
	
	private AWSStaticCredentialsProvider getCredentials() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
	}
	
	private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(){
		return new AwsClientBuilder.EndpointConfiguration(endpointUri, sqsRegion);
	}
		
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		
		return AmazonDynamoDBClientBuilder.standard()
				.withCredentials(getCredentials())
				.withEndpointConfiguration(getEndpointConfiguration())
				.build();
	}
	
	@Bean
	@Primary
    public AmazonSQSAsync amazonSQSAsync() {
		AmazonSQSAsyncClientBuilder a =  AmazonSQSAsyncClientBuilder.standard().
                withEndpointConfiguration(getEndpointConfiguration())
                .withCredentials(getCredentials());
       return a.build();
    }
	
    @Bean
	@Primary
    public AmazonSimpleEmailService amazonSimpleEmailService() {
    	AmazonSimpleEmailServiceClientBuilder a = AmazonSimpleEmailServiceClientBuilder.standard()
            .withEndpointConfiguration(getEndpointConfiguration());
    	
         a.setCredentials(getCredentials());
         return a.build();
    }
    
    @Bean
	@Primary
    public AmazonS3 amazonS3Client() {
    	return AmazonS3ClientBuilder.standard()
    		.withEndpointConfiguration(getEndpointConfiguration())
    		.withCredentials(getCredentials())
    		.withPathStyleAccessEnabled(true)
    		.build();
    }

}
