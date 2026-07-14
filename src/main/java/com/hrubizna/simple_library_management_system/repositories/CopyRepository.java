package com.hrubizna.simple_library_management_system.repositories;

import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import org.springframework.data.repository.CrudRepository;


public interface CopyRepository extends CrudRepository<BookCopyEntity, Long> {
    Iterable<BookCopyEntity> findByBookId(Long bookId);
}
