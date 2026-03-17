package pt.com.JoaoSimoes.services;

import org.flywaydb.core.extensibility.FlywayInvalidLicenseKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.JoaoSimoes.controllers.PersonController;
import pt.com.JoaoSimoes.data.dto.v1.PersonDTO;
import pt.com.JoaoSimoes.exception.RequiredObjectIsNullException;
import pt.com.JoaoSimoes.exception.ResourceNotFoundException;
import pt.com.JoaoSimoes.mapper.ObjectMapper;
import pt.com.JoaoSimoes.model.Person;
import pt.com.JoaoSimoes.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseListObjects;
import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseObject;

@Service
public class PersonService{

    @Autowired
    private PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public List<PersonDTO> findAll(){
        logger.info("Finding all People!");

        var persons = parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person){

        if(person == null)
            throw new RequiredObjectIsNullException();

        logger.info("Create one Person!");
        var entity = parseObject(person, Person.class);;
        var dto =  parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person){

        if(person == null)
            throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");

        var entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + person.getId()));

        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void  deleteById(Long id){
        logger.info("Deleting one Person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        repository.deleteById(entity.getId());
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).deleteById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
    }
}
