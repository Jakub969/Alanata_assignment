package com.hrubizna.simple_library_management_system.repositories;


import com.hrubizna.simple_library_management_system.TestDataUtil;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
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
public class BookCopyEntityRepositoryTest {

    private final CopyRepository copyTest;
    private final BookRepository bookTest;

    @Autowired
    public BookCopyEntityRepositoryTest(CopyRepository copyRepository, BookRepository bookRepository) {
        this.copyTest = copyRepository;
        this.bookTest = bookRepository;
    }

    @Test
    public void testThatCopyCanBeCreatedAndRecalled() {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntity);
        BookCopyEntity copyEntity = TestDataUtil.createTestBookCopyEntityA(bookEntity);
        copyTest.save(copyEntity);
        Optional<BookCopyEntity> result = copyTest.findById(copyEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(copyEntity);
    }

    @Test
    public void testThatMultipleCopiesCanBeCreatedAndRecalled() {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntity);
        BookCopyEntity copyEntityA = TestDataUtil.createTestBookCopyEntityA(bookEntity);
        copyTest.save(copyEntityA);
        BookCopyEntity copyEntityB = TestDataUtil.createTestBookCopyEntityB(bookEntity);
        copyTest.save(copyEntityB);
        BookCopyEntity copyEntityC = TestDataUtil.createTestBookCopyEntityC(bookEntity);
        copyTest.save(copyEntityC);
        Iterable<BookCopyEntity> result = copyTest.findAll();
        assertThat(result).contains(copyEntityA, copyEntityB, copyEntityC);
    }

    @Test
    public void testThatCopyCanBeUpdated() {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookTest.save(bookEntity);
        BookCopyEntity copyEntityA = TestDataUtil.createTestBookCopyEntityA(bookEntity);
        copyTest.save(copyEntityA);

        copyEntityA.setAvailable(false);
        copyTest.save(copyEntityA);

        Optional<BookCopyEntity> result = copyTest.findById(copyEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(copyEntityA);
    }
}
