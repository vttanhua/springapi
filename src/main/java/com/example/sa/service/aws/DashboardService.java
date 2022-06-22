package com.example.sa.service.aws;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sa.controller.thymeleaf.DashboardController;
import com.example.sa.dto.aws.CollaboratorDto;
import com.example.sa.dto.aws.TodoDto;
import com.example.sa.entity.aws.Person;
import com.example.sa.repository.aws.PersonRepository;
import com.example.sa.repository.aws.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class DashboardService {

  private final PersonRepository personRepository;
  private final TodoRepository todoRepository;

  public DashboardService(PersonRepository personRepository, TodoRepository todoRepository) {
    this.personRepository = personRepository;
    this.todoRepository = todoRepository;
  }

  public List<CollaboratorDto> getAvailableCollaborators(String email) {
   List<Person> collaborators = personRepository.findByEmailNot(email);

   return collaborators
     .stream()
     .map(person -> new CollaboratorDto(person.getId(), person.getName()))
     .collect(Collectors.toList());
  }

  public List<TodoDto> getAllOwnedAndSharedTodos(String email) {

    List<TodoDto> ownedTodos = todoRepository.findAllByOwnerEmailOrderByIdAsc(email)
      .stream()
      .map(todo -> new TodoDto(todo, false))
      .collect(Collectors.toList());

    List<TodoDto> collaborativeTodos = todoRepository.findAllByCollaboratorsEmailOrderByIdAsc(email)
      .stream()
      .map(todo -> new TodoDto(todo, true))
      .collect(Collectors.toList());

    ownedTodos.addAll(collaborativeTodos);

    return ownedTodos;
  }
}

