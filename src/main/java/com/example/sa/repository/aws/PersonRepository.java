package com.example.sa.repository.aws;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sa.entity.aws.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByName(String name);

  Optional<Person> findByEmail(String email);

  List<Person> findByNameNot(String name);

  List<Person> findByEmailNot(String email);
}
