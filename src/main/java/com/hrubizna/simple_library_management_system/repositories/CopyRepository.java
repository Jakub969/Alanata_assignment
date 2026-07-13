package com.hrubizna.simple_library_management_system.repositories;

import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CopyRepository extends CrudRepository<BookCopyEntity, Long>, PagingAndSortingRepository<BookCopyEntity, Long> {
    Iterable<BookCopyEntity> findByBookId(Long bookId);
}
