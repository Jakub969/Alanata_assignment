package com.hrubizna.simple_library_management_system.controllers;

import com.hrubizna.simple_library_management_system.services.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookViewController {

    private final BookService bookService;

    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(defaultValue = "title") String sortBy,
                           Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        model.addAttribute("books", bookService.findAll(pageable));
        return "books";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable Long id) {

        model.addAttribute("book", bookService.findById(id));
        return "book-details";
    }
}
