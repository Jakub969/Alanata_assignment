package com.hrubizna.simple_library_management_system.controllers;

import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import com.hrubizna.simple_library_management_system.services.BookService;
import com.hrubizna.simple_library_management_system.services.CopyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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

    @GetMapping(path = "/api/books/{id}/copies")
    public List<CopyDto> getCopies(@PathVariable Long id) {
        List<BookCopyEntity> copies = copyService.findByBookId(id);
        return copies.stream().map(copyMapper::mapTo).collect(Collectors.toList());
    }

    @PostMapping(path = "/api/books/{id}/copies")
    public ResponseEntity<CopyDto> addCopy(@Valid @PathVariable Long id, @RequestBody CopyDto copyDto) {
        BookEntity book = bookService.findById(id);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookCopyEntity copy = copyMapper.mapFrom(copyDto);

        copy.setBook(book);
        BookCopyEntity savedCopy = copyService.save(copy);
        CopyDto savedCopyDto = copyMapper.mapTo(savedCopy);
        return new ResponseEntity<>(savedCopyDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/api/books/{id}/copies/{copyId}")
    public ResponseEntity<CopyDto> updateCopy(@Valid @PathVariable Long id, @RequestBody CopyDto copyDto, @PathVariable Long copyId) {
        BookCopyEntity existingCopy = copyService.findById(copyId);

        if (existingCopy == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(copyId, copyDto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BookCopyEntity bookCopyEntity = copyMapper.mapFrom(copyDto);

        BookCopyEntity updatedCopyEntity = copyService.update(bookCopyEntity);

        return new ResponseEntity<>(
                copyMapper.mapTo(updatedCopyEntity),
                HttpStatus.OK
        );
    }
}
