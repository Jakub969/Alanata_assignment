package com.hrubizna.simple_library_management_system.mappers.implementation;

import com.hrubizna.simple_library_management_system.domain.dto.BookWithCopiesDto;
import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookWithCopiesMapperImplementation implements Mapper<BookEntity, BookWithCopiesDto> {

    //Implementation of special mapper for book so it will also show all copies for it
    @Override
    public BookWithCopiesDto mapTo(BookEntity bookEntity) {
        List<CopyDto> copies = bookEntity.getCopies()
                .stream()
                .map(copy -> CopyDto.builder()
                        .id(copy.getId())
                        .available(copy.isAvailable())
                        .build())
                .toList();

        return BookWithCopiesDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .isbn(bookEntity.getIsbn())
                .publishedYear(bookEntity.getPublishedYear())
                .copies(copies)
                .build();
    }

    @Override
    public BookEntity mapFrom(BookWithCopiesDto bookWithCopiesDto) {
        return null;
    }
}
