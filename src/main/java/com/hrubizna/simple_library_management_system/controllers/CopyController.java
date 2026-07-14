package com.hrubizna.simple_library_management_system.controllers;

import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import com.hrubizna.simple_library_management_system.services.BookService;
import com.hrubizna.simple_library_management_system.services.CopyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CopyController {

    private final CopyService copyService;
    private final BookService bookService;
    private final Mapper<BookCopyEntity, CopyDto> copyMapper;

    public CopyController(CopyService copyService, BookService bookService, Mapper<BookCopyEntity, CopyDto> copyMapper) {
        this.copyMapper = copyMapper;
        this.copyService = copyService;
        this.bookService = bookService;
    }

    @Operation(summary = "Get available copies for a book")
    @GetMapping(path = "/api/books/{id}/copies")
    public List<CopyDto> getCopies(@PathVariable Long id) {
        List<BookCopyEntity> copies = copyService.findByBookId(id);
        return copies.stream().map(copyMapper::mapTo).collect(Collectors.toList());
    }

    @Operation(summary = "Add a copy for a book")
    @PostMapping(path = "/api/books/{id}/copies")
    public ResponseEntity<CopyDto> addCopy(@PathVariable Long id) {
        BookEntity book = bookService.findById(id);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookCopyEntity copy = new BookCopyEntity();
        copy.setBook(book);
        BookCopyEntity savedCopy = copyService.save(copy);
        CopyDto savedCopyDto = copyMapper.mapTo(savedCopy);
        return new ResponseEntity<>(savedCopyDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update the availability status of a book copy")
    @PutMapping(path = "/api/books/{id}/copies/{copyId}")
    public ResponseEntity<CopyDto> updateCopy(@Valid @PathVariable Long id, @RequestBody CopyDto copyDto, @PathVariable Long copyId) {
        BookCopyEntity existingCopy = copyService.findById(copyId);

        if (existingCopy == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookCopyEntity bookCopyEntity = copyMapper.mapFrom(copyDto);
        bookCopyEntity.setId(copyId);

        BookCopyEntity updatedCopyEntity = copyService.update(bookCopyEntity);
        return new ResponseEntity<>(
                copyMapper.mapTo(updatedCopyEntity),
                HttpStatus.OK
        );
    }
}
