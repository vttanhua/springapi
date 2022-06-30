package com.example.sa.component;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.example.sa.dto.aws.TodoCollaborationNotification;
import com.example.sa.service.aws.TodoCollaborationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TodoSharingListener {
	
	private final MailSender mailSender;
	private final TodoCollaborationService todoCollaborationService;
	private final boolean autoConfirmCollaborations;
	private final String confirmEmailFromAddress;
	private final String externalUrl;
	
	@Autowired
	public TodoSharingListener(
			MailSender mailSender,
			TodoCollaborationService todoCollaborationService,
			@Value("${custom.auto-confirm-collaborations}") boolean autoConfirmCollaborations,
			@Value("${custom.confirm-email-from-address}") String confirmEmailFromAddress,
			@Value("${custom.extrenal-url}") String externalUrl
			) {
		this.mailSender = mailSender;
		this.todoCollaborationService = todoCollaborationService;
		this.autoConfirmCollaborations = autoConfirmCollaborations;
		this.confirmEmailFromAddress = confirmEmailFromAddress;
		this.externalUrl = externalUrl;
	}
	
	@SqsListener(value = "${custom.sharing-queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void listenToSharingMessages(TodoCollaborationNotification payload) throws InterruptedException {
		
		log.info("Incoming todo sharing payload: {}", payload);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(confirmEmailFromAddress);
		message.setTo(confirmEmailFromAddress);
		message.setSubject(payload.getCollaboratorEmail());
		message.setText(
				String.format(
					"Hi %s, \n\n" +
					"Someone shared a Todo from %s with you. \n\n" +
					"Information about the shared Todo item: \n\n" +
					"Title: %s \n" +
					"Description: %s \n" +
					"Priority %s \n" +
					"\n" +
					"You can accept the collaboration by clicking this link: " +
					"%s/todo/%s/collaborations/%s/confirm?token=%s " +
					"\n\n" +
					"Kind regards, \n" +
					"vttanhua",
					payload.getCollaboratorEmail(),
					externalUrl,
					payload.getTodoTitle(),
					payload.getTodoDescription(),
					payload.getTodoPriority(),
					payload.getTodoId(),
					payload.getCollaboratorId(),
					externalUrl,
					payload.getToken()
				)
		);
		mailSender.send(message);
		
		log.info("Successfully informed collaborator about shared todo.");
		
		if(autoConfirmCollaborations) {
			log.info("Auto-confirmed collaboration request for todo: {}", payload.getTodoId());
			Thread.sleep(2500);
			todoCollaborationService.confirmCollaboration(
					payload.getCollaboratorEmail(), payload.getTodoId(), 
					payload.getCollaboratorId(), payload.getToken());
		}

	}

}
