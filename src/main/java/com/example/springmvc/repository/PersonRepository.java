package com.example.springmvc.repository;

import com.example.springmvc.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

	Person findByEmail(String email);
}
