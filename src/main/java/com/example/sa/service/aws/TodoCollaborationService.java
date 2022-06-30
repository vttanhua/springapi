package com.example.sa.service.aws;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.dto.aws.TodoCollaborationNotification;
import com.example.sa.entity.aws.Person;
import com.example.sa.entity.aws.Todo;
import com.example.sa.entity.aws.TodoCollaborationRequest;
import com.example.sa.repository.aws.PersonRepository;
import com.example.sa.repository.aws.TodoCollaborationRequestRepository;
import com.example.sa.repository.aws.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TodoCollaborationService {

		private final TodoRepository todoRepository;
		private final PersonRepository personRepository;
		private final TodoCollaborationRequestRepository todoCollaborationRequestRepository;
		
		private final QueueMessagingTemplate queueMessagingTemplate;
		private final String todoSharingQueueName;
		
		private static final String INVALID_TODO_ID = "Invalid todo ID: ";
		private static final String INVALID_PERSON_ID = "Invalid person ID: ";
		private static final String INVALID_PERSON_EMAIL = "Invalid person Email: ";
		
		@Autowired
		public TodoCollaborationService(@Value("${custom.sharing-queue}") String todoSharingQueueName,
			TodoRepository todoRepository,
			PersonRepository personRepository,
			TodoCollaborationRequestRepository todoCollaborationRequestRepository,
			QueueMessagingTemplate queueMessagingTemplate
				) {
			this.todoRepository = todoRepository;
			this.personRepository = personRepository;
			this.todoCollaborationRequestRepository = todoCollaborationRequestRepository;
			this.queueMessagingTemplate = queueMessagingTemplate;
			this.todoSharingQueueName = todoSharingQueueName;
		}
		
		public String shareWithCollaborator(String email, Long todoId, Long collaboratorId) {
			Todo todo = todoRepository.findByIdAndOwnerEmail(todoId, email)
					.orElseThrow(()->new IllegalArgumentException( INVALID_TODO_ID + todoId));
			Person collaborator = personRepository
					.findById(collaboratorId)
					.orElseThrow(()-> new IllegalArgumentException(INVALID_PERSON_ID+collaboratorId));
			
			if(todoCollaborationRequestRepository.findByTodoAndCollaborator(todo, collaborator).isPresent()) {
				log.info("Collaboration request for todo {} with collaborator {} already exists!", todoId, collaboratorId);
				return collaborator.getName();
			}
			
			log.info("About to share todo with id {} with collaborator {}", todoId, collaboratorId);
			
			TodoCollaborationRequest collaboration = TodoCollaborationRequest.builder()
					.token(UUID.randomUUID().toString())
					.collaborator(collaborator)
					.todo(todo)
					.build();
			todo.getCollaborators().add(collaborator);
			
			todoCollaborationRequestRepository.save(collaboration);
			
			queueMessagingTemplate.convertAndSend(todoSharingQueueName, new TodoCollaborationNotification(collaboration));
			
			return collaborator.getName();
		}
		
		
		public boolean confirmCollaboration(String authenticatedUserEmail, Long todoId, Long collaboratorId, String token) {
			
			Person collaborator = personRepository
					.findByEmail(authenticatedUserEmail)
					.orElseThrow(() -> new IllegalArgumentException(INVALID_PERSON_EMAIL+ authenticatedUserEmail));
			
			if(!collaborator.getId().equals(collaboratorId))
				return false;
			
			TodoCollaborationRequest collaborationRequest = todoCollaborationRequestRepository
					.findByTodoIdAndCollaboratorId(todoId, collaboratorId);
			
			if(collaborationRequest == null || !collaborationRequest.getToken().equals(token)) {
				return false;
			}
			
			Todo todo = todoRepository
					.findById(todoId)
					.orElseThrow(() -> new IllegalArgumentException(INVALID_TODO_ID+todoId));
			
			todo.addCollaborator(collaborator);
			
			todoCollaborationRequestRepository.delete(collaborationRequest);
			
			return true;
		}
		
}
