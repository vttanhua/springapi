package com.example.sa.service.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.waiters.WaiterParameters;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class TodoAttachmentS3Service {
	
	private final AmazonS3 s3Client;
	private final String bucketUrlFormat;
	
	
	@Autowired
	public TodoAttachmentS3Service(AmazonS3 s3Client, 
			@Value("${custom.s3bucketurl.format}") String bucketUrlFormat) {
		this.s3Client = s3Client;	
		this.bucketUrlFormat = bucketUrlFormat;
	}

	public String getBucketLocation(String bucketName) {
		log.info("bucket is in {} location", this.s3Client.getBucketLocation(bucketName));
		String location = String.format(this.bucketUrlFormat, bucketName,
				this.s3Client.getBucketLocation(bucketName));
		log.info("bucket is in {} location full location is {}", this.s3Client.getBucketLocation(bucketName), location);
		return location;
	}

	public List<S3ObjectSummary> listObjects(String bucketName) {
		return s3Client.listObjects(bucketName).getObjectSummaries();
	}

	public boolean fileExists(String bucketName, String filename) {
		return s3Client.doesObjectExist(bucketName, filename);
	}
	
	public boolean deleteFile(String bucketName, String key) {
		try {
		s3Client.deleteObject(bucketName, key);
		}catch(Exception e) {
			log.error("Error while deleting file from s3 bucket {}",e);
			return false;
		}
		return true;
	}
	
	public void uploadFile(String bucketName, String filename, MultipartFile multipart) throws IOException {
		ObjectMetadata o = new ObjectMetadata();
		o.setContentLength(multipart.getSize());
		o.setContentType(multipart.getContentType());
		o.addUserMetadata("relatedTodo", "todoTitle");
		PutObjectRequest r = new PutObjectRequest(
				bucketName,
				filename,
				multipart.getInputStream(), o);
		PutObjectResult result = s3Client.putObject(r);
		GetObjectMetadataRequest g = new GetObjectMetadataRequest(bucketName,filename);
		s3Client.waiters().objectExists().run(new WaiterParameters<GetObjectMetadataRequest>(g));//wait for completion
		//s3Client.setObjectAcl(bucketName,filename, CannedAccessControlList.PublicRead);//to make it public if needed 
		
		log.info("Upload result was {} ",result);	
	}

	
	public InputStream getFile(String bucketName, String filename) {
		S3Object s = s3Client.getObject(bucketName,filename);
		return s.getObjectContent();
	}
	
}
