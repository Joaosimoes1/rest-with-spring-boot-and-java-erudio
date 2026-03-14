package pt.com.JoaoSimoes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.JoaoSimoes.data.dto.PersonDTO;
import pt.com.JoaoSimoes.exception.ResourceNotFoundException;
import pt.com.JoaoSimoes.mapper.ObjectMapper;
import pt.com.JoaoSimoes.model.Person;
import pt.com.JoaoSimoes.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseListObjects;
import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseObject;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<PersonDTO> findAll(){
        logger.info("Finding all People!");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Create one Person!");
        var entity = parseObject(person, Person.class);;
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one Person!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + person.getId()));

        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void  deleteById(Long id){
        logger.info("Deleting one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        repository.deleteById(entity.getId());
    }
}
