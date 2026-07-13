package com.hrubizna.simple_library_management_system.repositories;


import com.hrubizna.simple_library_management_system.TestDataUtil;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryTest {

    private final BookRepository bookTest;

    @Autowired
    public BookEntityRepositoryTest(BookRepository bookRepository) {
        this.bookTest = bookRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntity);
        Optional<BookEntity> result = bookTest.findById(bookEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRetrieved() {
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntityA);
        BookEntity bookEntityB = TestDataUtil.createTestBookEntityB();
        bookTest.save(bookEntityB);
        BookEntity bookEntityC = TestDataUtil.createTestBookEntityC();
        bookTest.save(bookEntityC);
        Iterable<BookEntity> result = bookTest.findAll();
        assertThat(result).contains(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntityA);
        bookEntityA.setAuthor("A");
        bookTest.save(bookEntityA);
        Optional<BookEntity> result = bookTest.findById(bookEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        BookEntity bookEntityA = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntityA);
        bookTest.deleteById(bookEntityA.getId());
        Optional<BookEntity> result = bookTest.findById(bookEntityA.getId());
        assertThat(result).isEmpty();
    }
}
