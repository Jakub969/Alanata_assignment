package com.hrubizna.simple_library_management_system.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

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
}
