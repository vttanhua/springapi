package com.example.sa.entity.aws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data                     
@DynamoDBTable(tableName="breadcrumbsv2")
public class Breadcrumb {
	
	@DynamoDBHashKey(attributeName="username")
	private String username;
	
	@DynamoDBRangeKey(attributeName="timestamp")
	private String timestamp;
	
	@DynamoDBAttribute(attributeName="uri")
	private String uri;
	
	@DynamoDBAttribute(attributeName="sessionId")
	private String sessionId;
	

}
