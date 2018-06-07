package com.example.springmvc.controller;

import com.example.springmvc.model.Person;
import com.example.springmvc.repository.PersonRepository;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {

    private PersonRepository personRepository;
   

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
  


	@RequestMapping(path = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/persons/add", method = RequestMethod.GET)
    public String createPerson(Model model) {
        model.addAttribute("person", new Person());
        return "edit";
    }

    @RequestMapping(path = "persons", method = RequestMethod.POST)
    public String savePerson(Model model,Person person) {
    	EmailValidator emailValid = new EmailValidator();
    	Boolean ok = emailValid.isValid(person.getEmail(), null);
    	String message = "Email not valid";
    	System.out.println("ok="+ok);
    	if(!ok) {
			model.addAttribute("message",message);
			//return "message";
    	}
    	try {
    		String message1 = "Save with succes";
    		this.isDuplicateEmail(person);
    		model.addAttribute("message",message1);
    	}catch(Exception e) {
    		e.getMessage();
    	}
    	
        return "message";
    	
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public String getAllPersons(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "persons";
    }

    @RequestMapping(path = "/persons/edit/{id}", method = RequestMethod.GET)
    public String editPerson(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("person", personRepository.findOne(id));
        return "edit";
    }

    @RequestMapping(path = "/persons/delete/{id}", method = RequestMethod.GET)
    public String deletePerson(@PathVariable(name = "id") String id) {
        personRepository.delete(id);
        return "redirect:/persons";
    }
    
    public void isDuplicateEmail(Person person) {
    	Person personOptional = personRepository.findByEmail(person.getEmail());
    	if (personOptional != null) {
			person.setId(personOptional.getId());
			personOptional.setId(person.getId());
			personOptional.setName(person.getName());
			personOptional.setEmail(person.getEmail());
		    personRepository.save(personOptional);			
		
		} else {
			personRepository.save(person);
		}
    }
}
