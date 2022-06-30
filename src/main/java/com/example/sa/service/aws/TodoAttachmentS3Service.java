package com.example.sa.service.aws;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class TodoAttachmentS3Service {
	
	private String bucketName;
	private final AmazonS3 s3Client;
	private String bucketLocation;
	private final String bucketUrlFormat;
	
	
	@Autowired
	public TodoAttachmentS3Service(AmazonS3 s3Client, 
			@Value("${custom.s3bucketurl.format}") String bucketUrlFormat) {
		this.s3Client = s3Client;	
		this.bucketUrlFormat = bucketUrlFormat;
	}
	
	protected void calculateBucketLocation() {
		log.info("bucket is in {} location", this.s3Client.getBucketLocation(bucketName));
		this.setBucketLocation(String.format(this.bucketUrlFormat, bucketName,
				this.s3Client.getBucketLocation(bucketName)));
		log.info("bucket is in {} location full location is {}", this.s3Client.getBucketLocation(bucketName), this.getBucketLocation());
	}

	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
		calculateBucketLocation();
	}

	public String getBucketLocation() {
		return bucketLocation;
	}

	protected void setBucketLocation(String bucketLocation) {
		this.bucketLocation = bucketLocation;
	}

	public List<S3ObjectSummary> listObjects() {
		return s3Client.listObjects(bucketName).getObjectSummaries();
	}	
}
