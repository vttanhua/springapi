package com.example.sa.controller.thymeleaf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.sa.service.aws.TodoAttachmentS3Service;

@Controller
public class S3Dashboard {

	private final TodoAttachmentS3Service s3AttachmentService;
	
	private String bucketName;
	public S3Dashboard(TodoAttachmentS3Service s3AttachmentService,
			@Value("${custom.s3bucket.todoattachments}") String bucketName) {
		this.s3AttachmentService = s3AttachmentService;
		this.bucketName=bucketName;
	}
	
	@GetMapping("/S3Dashboard")
	public ModelAndView getS3Listing() {
		this.s3AttachmentService.setBucketName(bucketName);
		ModelAndView modelAndView = new ModelAndView("S3Dashboard");
		modelAndView.addObject("message", "Spring Boot with aws");
		modelAndView.addObject("bucketName", this.s3AttachmentService.getBucketName());
		modelAndView.addObject("bucketLocation", this.s3AttachmentService.getBucketLocation());
		modelAndView.addObject("availableFiles", s3AttachmentService.listObjects());
		return modelAndView;
	}
}
