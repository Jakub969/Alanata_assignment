package com.hrubizna.simple_library_management_system.controllers;


import com.hrubizna.simple_library_management_system.TestDataUtil;
import com.hrubizna.simple_library_management_system.domain.dto.BookDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerTests {

    private final BookService bookService;

    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    @Autowired
    public BookControllerTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.mapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookService.save(bookEntity);
        String bookJson = mapper.writeValueAsString(bookEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(bookJson)).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA();
        String bookJson = mapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)).andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Tma")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Jozef Karika")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-8-05-514362-0")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.publishedYear").value(2015));
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookService.save(bookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Tma"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Jozef Karika"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-8-05-514362-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publishedYear").value(2015));
    }

    @Test
    public void testThatGetBookReturnsHttp200WhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookService.save(bookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + bookEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookReturnsBookWhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        bookService.save(bookEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + bookEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Tma"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Jozef Karika"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-8-05-514362-0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishedYear").value(2015));
    }

    @Test
    public void testThatGetBookReturnsHttp404WhenBookDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/99999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateBookReturnsHttp404WhenBookDoesNotExist() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA();
        String bookJson = mapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/99999").contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateBookReturnsHttp200WhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBookEntity = bookService.save(bookEntity);
        BookDto bookDto = TestDataUtil.createTestBookDtoA();
        bookDto.setId(savedBookEntity.getId());
        String bookJson = mapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + savedBookEntity.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBookEntity = bookService.save(bookEntity);

        BookEntity bookDto = TestDataUtil.createTestBookEntityB();
        bookDto.setId(savedBookEntity.getId());
        String bookJson = mapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + savedBookEntity.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedBookEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDto.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishedYear").value(bookDto.getPublishedYear()));
    }

    @Test
    public void testThatDeleteBookReturnsHttp204ForExistingBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBookEntity = bookService.save(bookEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/" + savedBookEntity.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteBookReturnsHttp204ForNonExistingBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/99999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
