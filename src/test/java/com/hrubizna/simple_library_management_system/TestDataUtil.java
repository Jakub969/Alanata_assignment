package com.hrubizna.simple_library_management_system;

import com.hrubizna.simple_library_management_system.domain.dto.BookDto;
import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;

public class TestDataUtil {

    private TestDataUtil() {}

    public static BookEntity createTestBookEntityA() {
        return BookEntity.builder()
                .title("Tma")
                .author("Jozef Karika")
                .isbn("978-8-05-514362-0")
                .publishedYear(2015)
                .build();
    }

    public static BookDto createTestBookDtoA() {
        return BookDto.builder()
                .title("Tma")
                .author("Jozef Karika")
                .isbn("978-8-05-514362-0")
                .publishedYear(2015)
                .build();
    }

    public static BookEntity createTestBookEntityB() {
        return BookEntity.builder()
                .title("Dune")
                .author("Frank Patrick Herbert")
                .isbn("978-8-08-159647-6")
                .publishedYear(1965)
                .build();
    }

    public static BookEntity createTestBookEntityC() {
        return BookEntity.builder()
                .title("Fellowship of the Ring")
                .author("John Ronald Reuel Tolkien")
                .isbn("978-0-54-795201-7")
                .publishedYear(1965)
                .build();
    }

    public static BookCopyEntity createTestBookCopyEntityA(final BookEntity bookEntity) {
        return BookCopyEntity.builder()
                .book(bookEntity)
                .available(true)
                .build();
    }

    public static CopyDto createTestCopyDtoA(final BookDto bookDto) {
        return CopyDto.builder()
                .book(bookDto)
                .available(true)
                .build();
    }

    public static BookCopyEntity createTestBookCopyEntityB(final BookEntity bookEntity) {
        return BookCopyEntity.builder()
                .book(bookEntity)
                .available(false)
                .build();
    }

    public static BookCopyEntity createTestBookCopyEntityC(final BookEntity bookEntity) {
        return BookCopyEntity.builder()
                .book(bookEntity)
                .available(false)
                .build();
    }
}
