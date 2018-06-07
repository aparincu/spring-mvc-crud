package com.example.springmvc.boot;

import com.example.springmvc.model.Person;
import com.example.springmvc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Person person1 = new Person();
        person1.setName("Milky Bar");
        person1.setEmail("milky@yahoo.com");
      

        personRepository.save(person1);

        Person person2 = new Person();
        person2.setName("Almond Bar");
        person2.setEmail("almond@yahoo.com");

        personRepository.save(person2);
    }
}
