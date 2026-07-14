package com.hrubizna.simple_library_management_system.services;

import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookEntity save(BookEntity book);
    List<BookEntity> findAll();
    Page<BookEntity> findAll(Pageable pageable);
    BookEntity findById(Long id);
    BookEntity update(BookEntity book);
    void deleteById(Long id);
}
