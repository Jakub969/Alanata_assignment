package com.hrubizna.simple_library_management_system.services;

import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;

import java.util.List;

public interface CopyService {
    List<BookCopyEntity> findByBookId(Long id);
    BookCopyEntity save(BookCopyEntity bookCopyEntity);
    BookCopyEntity update(BookCopyEntity bookCopyEntity);
    BookCopyEntity findById(Long id);
}
