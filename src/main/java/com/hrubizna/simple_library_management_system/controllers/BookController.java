package com.hrubizna.simple_library_management_system.controllers;


import com.hrubizna.simple_library_management_system.domain.dto.BookDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import com.hrubizna.simple_library_management_system.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @GetMapping(path = "/api/books")
    public List<BookDto> getAllBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping(path = "/api/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookEntity foundBookEntity = bookService.findById(id);

        if (foundBookEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookDto bookDto = bookMapper.mapTo(foundBookEntity);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PutMapping(path = "/api/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        BookEntity foundBookEntity = bookService.findById(id);
        if (foundBookEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.update(bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/books/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
