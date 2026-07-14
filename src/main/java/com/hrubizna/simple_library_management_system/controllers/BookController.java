package com.hrubizna.simple_library_management_system.controllers;


import com.hrubizna.simple_library_management_system.domain.dto.BookDto;
import com.hrubizna.simple_library_management_system.domain.dto.BookWithCopiesDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import com.hrubizna.simple_library_management_system.services.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class BookController {

    private final Mapper<BookEntity, BookDto> bookMapper;
    private final Mapper<BookEntity, BookWithCopiesDto> bookWithCopiesMapper;
    private final BookService bookService;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper, Mapper<BookEntity, BookWithCopiesDto> bookWithCopiesMapper) {
        this.bookMapper = bookMapper;
        this.bookWithCopiesMapper = bookWithCopiesMapper;
        this.bookService = bookService;
    }

    @GetMapping(path = "/api/books")
    public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
    }

    @PostMapping(path = "/api/books")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/books/{id}")
    public ResponseEntity<BookWithCopiesDto> getBookById(@PathVariable Long id) {

        BookEntity foundBookEntity = bookService.findById(id);

        if (foundBookEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookWithCopiesDto bookDto = bookWithCopiesMapper.mapTo(foundBookEntity);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PutMapping(path = "/api/books/{id}")
    public ResponseEntity<BookDto> updateBook(@Valid @PathVariable Long id, @RequestBody BookDto bookDto) {
        BookEntity foundBookEntity = bookService.findById(id);
        if (foundBookEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookEntity.setId(id);
        BookEntity savedBookEntity = bookService.update(bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/books/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
