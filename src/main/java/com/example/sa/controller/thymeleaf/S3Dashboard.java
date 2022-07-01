package com.example.sa.controller.thymeleaf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.sa.service.aws.TodoAttachmentS3Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/S3Dashboard")
public class S3Dashboard {

	private final TodoAttachmentS3Service s3AttachmentService;
	
	private String bucketName;
	
	@Autowired
	public S3Dashboard(TodoAttachmentS3Service s3AttachmentService,
			@Value("${custom.s3bucket.todoattachments}") String bucketName) {
		this.s3AttachmentService = s3AttachmentService;
		this.bucketName=bucketName;
	}
	
	@GetMapping
	public ModelAndView getS3Listing() {
		ModelAndView modelAndView = new ModelAndView("S3Dashboard");
		modelAndView.addObject("message", "Spring Boot with aws");
		modelAndView.addObject("bucketName", this.bucketName);
		modelAndView.addObject("bucketLocation", this.s3AttachmentService.getBucketLocation(this.bucketName));
		modelAndView.addObject("availableFiles", s3AttachmentService.listObjects(this.bucketName));
		return modelAndView;
	}
	
	@GetMapping(value="/download")
	public void getFile(Model model,@RequestParam("fileName") String fileName, HttpServletResponse response) {
		response.setHeader("Content-disposition", "attachment;filename="+fileName);
		try(InputStream is =this.s3AttachmentService.getFile(this.bucketName, fileName); PrintWriter out = response.getWriter()){
			int c;
            while ((c = is.read()) != -1) {
            	 out.write(c);
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@PostMapping
	public ModelAndView uploadNewS3File(Model model, String description,
            @RequestParam("file") MultipartFile multipart) {
		log.info("Upload new file to S3****** description {} already exists{}",description,s3AttachmentService.fileExists(this.bucketName, multipart.getOriginalFilename()));
		try {
			s3AttachmentService.uploadFile(this.bucketName, multipart.getOriginalFilename(), multipart);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error while uploading new file to s3: {}", e);
		}catch(AmazonServiceException e) {
			log.error("Error while uploading new file to s3: {}", e);
		}catch(SdkClientException e) {
			log.error("Error while uploading new file to s3: {}", e);
		}

		return getS3Listing();
	}
}
