package pt.com.JoaoSimoes.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.com.JoaoSimoes.controllers.docs.BookControllerDocs;
import pt.com.JoaoSimoes.data.dto.BookDTO;
import pt.com.JoaoSimoes.data.dto.v1.PersonDTO;
import pt.com.JoaoSimoes.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService service;

    @GetMapping
    @Override
    public List<BookDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Override
    public BookDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    @Override
    public BookDTO create(@RequestBody BookDTO book){
        return service.create(book);
    }

    @PutMapping
    @Override
    public BookDTO update(@RequestBody BookDTO book){
        return service.update(book);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
