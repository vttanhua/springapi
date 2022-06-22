package com.example.sa.repository.aws;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sa.entity.aws.Person;
import com.example.sa.entity.aws.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByOwner(Person person);

  List<Todo> findAllByOwnerEmail(String email);

  List<Todo> findAllByOwnerEmailOrderByIdAsc(String email);
  
  List<Todo> findAllByCollaboratorsEmailOrderByIdAsc(String email);

  Optional<Todo> findByIdAndOwnerEmail(Long todoId, String email);
}

