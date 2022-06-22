package com.example.sa.repository.aws;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sa.entity.aws.Person;
import com.example.sa.entity.aws.Todo;
import com.example.sa.entity.aws.TodoCollaborationRequest;

@Repository
public interface TodoCollaborationRequestRepository extends CrudRepository<TodoCollaborationRequest, Long> {

  Optional<TodoCollaborationRequest> findByTodoAndCollaborator(Todo todo, Person person);
  TodoCollaborationRequest findByTodoIdAndCollaboratorId(Long todoId, Long collaboratorId);
}
