package pt.com.JoaoSimoes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.JoaoSimoes.controllers.BookController;
import pt.com.JoaoSimoes.data.dto.BookDTO;
import pt.com.JoaoSimoes.data.dto.v1.PersonDTO;
import pt.com.JoaoSimoes.exception.RequiredObjectIsNullException;
import pt.com.JoaoSimoes.exception.ResourceNotFoundException;
import pt.com.JoaoSimoes.model.Book;
import pt.com.JoaoSimoes.model.Person;
import pt.com.JoaoSimoes.repository.BookRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseListObjects;
import static pt.com.JoaoSimoes.mapper.ObjectMapper.parseObject;

@Service
public class BookService {
    
    @Autowired
    private BookRepository repository;

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public List<BookDTO> findAll(){
        logger.info("Finding all People!");

        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
    }

    public BookDTO findById(Long id){
        logger.info("Finding one Book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        var dto =  parseObject(entity, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO book){

        if(book == null)
            throw new RequiredObjectIsNullException();

        logger.info("Create one Person!");
        var entity = parseObject(book, Book.class);;
        var dto =  parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(BookDTO book){

        if(book == null)
            throw new RequiredObjectIsNullException();

        logger.info("Updating one Book!");

        var entity = repository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + book.getId()));

        entity.setAuthor(book.getAuthor());
        entity.setLaunch_date(book.getLaunch_date());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var dto = parseObject(repository.save(entity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deleteById(Long id){
        logger.info("Deleting one Book!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        repository.deleteById(entity.getId());
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).deleteById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
    }

}
