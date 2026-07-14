package com.hrubizna.simple_library_management_system.services.implementation;

import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.repositories.BookRepository;
import com.hrubizna.simple_library_management_system.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public BookEntity findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookEntity update(BookEntity book) {
        return bookRepository.findById(book.getId()).map(existingBook -> {
            if (book.getTitle() != null) existingBook.setTitle(book.getTitle()) ;
            if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
            if (book.getIsbn() != null) existingBook.setIsbn(book.getIsbn());
            if (book.getPublishedYear() != null) existingBook.setPublishedYear(book.getPublishedYear());
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
