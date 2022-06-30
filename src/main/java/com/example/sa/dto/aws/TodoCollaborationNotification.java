package com.example.sa.dto.aws;

import java.time.LocalDate;

import com.example.sa.entity.aws.Priority;
import com.example.sa.entity.aws.TodoCollaborationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoCollaborationNotification {
	
	  private String collaboratorEmail;
	  private String collaboratorName;
	  private Long collaboratorId;

	  private String todoTitle;
	  private String todoDescription;
	  private Priority todoPriority;
	  private Long todoId;

	  private String token;
	  
	  public TodoCollaborationNotification(TodoCollaborationRequest todoCollaborationRequest) {
		    this.collaboratorEmail = todoCollaborationRequest.getCollaborator().getEmail();
		    this.collaboratorName = todoCollaborationRequest.getCollaborator().getName();
		    this.collaboratorId = todoCollaborationRequest.getCollaborator().getId();
		    this.todoTitle = todoCollaborationRequest.getTodo().getTitle();
		    this.todoDescription = todoCollaborationRequest.getTodo().getDescription();
		    this.todoId = todoCollaborationRequest.getTodo().getId();
		    this.todoPriority = todoCollaborationRequest.getTodo().getPriority();
		    this.token = todoCollaborationRequest.getToken();
		  }
}
