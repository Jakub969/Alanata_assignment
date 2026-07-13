package com.hrubizna.simple_library_management_system.repositories;

import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CopyRepository extends CrudRepository<BookCopyEntity, Long>, PagingAndSortingRepository<BookCopyEntity, Long> {
    List<BookCopyEntity> findByBookId(Long bookId);
}
