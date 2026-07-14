package com.hrubizna.simple_library_management_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrubizna.simple_library_management_system.TestDataUtil;
import com.hrubizna.simple_library_management_system.domain.dto.BookDto;
import com.hrubizna.simple_library_management_system.domain.dto.CopyDto;
import com.hrubizna.simple_library_management_system.domain.entities.BookCopyEntity;
import com.hrubizna.simple_library_management_system.domain.entities.BookEntity;
import com.hrubizna.simple_library_management_system.mappers.Mapper;
import com.hrubizna.simple_library_management_system.services.BookService;
import com.hrubizna.simple_library_management_system.services.CopyService;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CopyControllerTests {

    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    private final Mapper<BookEntity, BookDto> bookDtoMapper;

    private final Mapper<BookCopyEntity, CopyDto> copyDtoMapper;

    private final CopyService copyService;

    private final BookService bookService;

    @Autowired
    public CopyControllerTests(MockMvc mockMvc, CopyService copyService, BookService bookService, Mapper<BookEntity, BookDto> bookDtoMapper, Mapper<BookCopyEntity, CopyDto> copyDtoMapper) {
        this.mockMvc = mockMvc;
        this.copyService = copyService;
        this.bookService = bookService;
        this.mapper = new ObjectMapper();
        this.bookDtoMapper = bookDtoMapper;
        this.copyDtoMapper = copyDtoMapper;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBook = bookService.save(bookEntity);
        BookDto bookDto = bookDtoMapper.mapTo(savedBook);
        CopyDto copyDto = TestDataUtil.createTestCopyDtoA(bookDto);
        String json = mapper.writeValueAsString(copyDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/" + savedBook.getId() + "/copies")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatUpdateCopyAvaliabilityReturnsHttpStatus200() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBook = bookService.save(bookEntity);
        BookCopyEntity bookCopyEntity = TestDataUtil.createTestBookCopyEntityA(savedBook);
        BookCopyEntity savedCopy = copyService.save(bookCopyEntity);
        savedCopy.setAvailable(false);
        CopyDto copyDto = copyDtoMapper.mapTo(savedCopy);
        String json = mapper.writeValueAsString(copyDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + savedBook.getId() + "/copies/" + savedCopy.getId())
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListCopiesReturnsHttpStatus200() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA();
        BookEntity savedBook = bookService.save(bookEntity);
        BookCopyEntity bookCopyEntityA = TestDataUtil.createTestBookCopyEntityA(bookEntity);
        copyService.save(bookCopyEntityA);
        BookCopyEntity bookCopyEntityB = TestDataUtil.createTestBookCopyEntityB(bookEntity);
        copyService.save(bookCopyEntityB);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + savedBook.getId() + "/copies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
