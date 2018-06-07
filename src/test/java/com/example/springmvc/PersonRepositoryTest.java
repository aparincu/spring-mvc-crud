package com.example.springmvc;



import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import com.example.springmvc.model.Person;
import com.example.springmvc.repository.PersonRepository;


/**
 * 
 * @author Alex Parincu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class PersonRepositoryTest extends BaseEndpointTest {
	


	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PersonRepository personService;
	
	private Person testPerson;

	
    @Before
    public void setup() throws Exception {
    	super.setup();

    	// create test persons
    	personService.save(createPerson("Jack","Bauer@yahoo.com"));
    	personService.save(createPerson("Chloe O'Brian","oBrian@yahoo.com"));
    	personService.save(createPerson("Kim", "kim@yahoo.com"));
    	personService.save(createPerson("David","david@yahoo.com"));
    	personService.save(createPerson("Michelle Dessler","dessler@yahoo.com"));

    	List<Person> persons = (List<Person>) personService.findAll();
		assertNotNull(persons);
		
		
    }

 

    @Test
    public void requestBodyValidationValidJson() throws Exception {
    	
    	String content = json("not valid json");
    	mockMvc.perform(
    			post("/persons")
    			.accept(JSON_MEDIA_TYPE)
    			.content(content)
    			.contentType(JSON_MEDIA_TYPE))
    	.andDo(print());
    }

    @Test
    public void handleHttpRequestMethodNotSupportedException() throws Exception {
    	
    	String content = json(testPerson);
    	
    	mockMvc.perform(
    			delete("/persons") //not supported method
    			.accept(JSON_MEDIA_TYPE)
    			.content(content)
    			.contentType(JSON_MEDIA_TYPE))
    	.andDo(print())
    	.andExpect(status().isMethodNotAllowed())
    	.andExpect(content().string(""))
    	;
    }
    
    @Test
    public void createPerson() throws Exception {
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(JSON_MEDIA_TYPE);
		
    	Person person = createPerson("name","email");
    	person.setName("Name");
    	person.setEmail("name@yahoo.com");
    	person.setId("1");

    	String content = json(person);
    	
		MvcResult result = mockMvc.perform(
				post("/createPerson")
				.accept(JSON_MEDIA_TYPE)
				.content(content)
				.contentType(JSON_MEDIA_TYPE))
		.andDo(print())
	    .andReturn();
    	
		logger.info("content="+ result.getResponse().getContentAsString());
		
    }
   
    
    

	private Person createPerson(String name,String email) {
		Person person = new Person();
		person.setName(name);
		person.setEmail(email);
		return person;
	}

}
