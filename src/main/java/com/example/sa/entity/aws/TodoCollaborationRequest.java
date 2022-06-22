package com.example.sa.entity.aws;


import javax.persistence.*;

@Entity
public class TodoCollaborationRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @ManyToOne
  @JoinColumn(name = "collaborator_id")
  private Person collaborator;

  @ManyToOne
  private Todo todo;
}
