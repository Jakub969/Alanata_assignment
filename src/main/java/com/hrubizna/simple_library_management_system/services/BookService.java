package com.hrubizna.simple_library_management_system.services;

import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import java.util.List;

public interface BookService {
    BookEntity save(BookEntity book);
    List<BookEntity> findAll();
    BookEntity findById(Long id);
    BookEntity update(BookEntity book);
    void delete(Long id);
}
