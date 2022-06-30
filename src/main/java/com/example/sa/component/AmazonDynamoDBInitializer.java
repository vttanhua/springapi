package com.example.sa.component;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AmazonDynamoDBInitializer {

		private final AmazonDynamoDB amazonDynamoDB;
		private final String breadcrumbTableName;
		
		@Autowired
		public AmazonDynamoDBInitializer(AmazonDynamoDB amazonDynamoDB,
				@Value("${custom.breadcrumb-table-name}") String breadcrumbTableName) {
			this.amazonDynamoDB = amazonDynamoDB;
			this.breadcrumbTableName = breadcrumbTableName;
		}
		
		@PostConstruct
		public void initializeDynamoDBTables() {
			DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
			
			try {
				List<KeySchemaElement> keySchemaElementList = Arrays.asList(
						new KeySchemaElement("username", KeyType.HASH),
						new KeySchemaElement("timestamp", KeyType.RANGE)
						);
				List<AttributeDefinition> attributeDefinitionList = Arrays.asList(
						new AttributeDefinition("username", ScalarAttributeType.S),
						new AttributeDefinition("timestamp", ScalarAttributeType.S)
						);
				
				Table table = dynamoDB.createTable(
						breadcrumbTableName,
						keySchemaElementList,
						attributeDefinitionList,
						new ProvisionedThroughput(10L,10L));
				table.waitForActive();
			}catch(Exception e) {
				log.error("Unable to create DynamoBD table: {}", e.getMessage());
			}
			
		}
		
		
}
