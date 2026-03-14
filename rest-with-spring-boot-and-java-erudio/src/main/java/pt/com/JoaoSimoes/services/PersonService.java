package pt.com.JoaoSimoes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.JoaoSimoes.exception.ResourceNotFoundException;
import pt.com.JoaoSimoes.model.Person;
import pt.com.JoaoSimoes.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<Person> findAll(){
        logger.info("Finding all People!");

        return repository.findAll();
    }

    public Person findById(Long id){
        logger.info("Finding one Person!");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
    }

    public Person create(Person person){
        logger.info("Create one Person!");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating one Person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + person.getId()));

        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void  deleteById(Long id){
        logger.info("Deleting one Person!");
        repository.deleteById(id);
    }
}
