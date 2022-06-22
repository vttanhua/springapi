package com.example.sa.entity.aws;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Slf4j
@Data
@Entity
public class Reminder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;
}
