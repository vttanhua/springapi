package com.example.sa.dto.aws;

import java.time.LocalDate;

import com.example.sa.entity.aws.Todo;
import com.example.sa.service.aws.DashboardService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TodoDto {

	  private Long id;
	  private String title;
	  private int amountOfCollaborators;
	  private int amountOfCollaborationRequests;
	  private LocalDate dueDate;
	  private boolean isCollaboration;
	 
	  public TodoDto(Todo todo, boolean isCollaboration) {
		    this.id = todo.getId();
		    this.title = todo.getTitle();
		    this.amountOfCollaborationRequests = todo.getCollaborationRequests().size();
		    this.amountOfCollaborators = todo.getCollaborators().size();
		    this.dueDate = todo.getDueDate();
		    this.isCollaboration = isCollaboration;
		  }	  
}
