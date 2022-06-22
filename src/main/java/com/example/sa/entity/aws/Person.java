package com.example.sa.entity.aws;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Data
@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String name;

  @NotEmpty
  @Column(unique = true)
  private String email;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
  private List<Todo> ownedTodos;

  @ManyToMany(mappedBy = "collaborators")
  private List<Todo> collaborativeTodos;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "collaborator")
  private List<TodoCollaborationRequest> collaborationRequests;


}
