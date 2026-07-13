package com.hrubizna.simple_library_management_system.repositories;

import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long> {

}
