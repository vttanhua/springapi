package com.example.sa.service.aws;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.example.sa.entity.aws.Person;
import com.example.sa.entity.aws.Status;
import com.example.sa.entity.aws.Todo;
import com.example.sa.repository.aws.PersonRepository;
import com.example.sa.repository.aws.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final PersonRepository personRepository;

  @Autowired
  public TodoService(
    TodoRepository todoRepository,
    PersonRepository personRepository) {
    this.todoRepository = todoRepository;
    this.personRepository = personRepository;
  }

  public Todo save(Todo todo) {
    if (todo.getOwner() == null) {

      OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      String email = user.getEmail();
      Person person = personRepository.findByEmail(email).orElse(null);

      if (person == null) {
        Person newUser = new Person();
        newUser.setName(user.getAttribute("name"));
        newUser.setEmail(email);

        person = personRepository.save(newUser);
      }
      todo.setOwner(person);
      todo.setStatus(Status.OPEN);
    }

    return todoRepository.save(todo);
  }

  public Optional<Todo> findById(Long id) {
    return this.todoRepository.findById(id);
  }

  public void delete(Todo todo) {
    this.todoRepository.delete(todo);
  }
}

