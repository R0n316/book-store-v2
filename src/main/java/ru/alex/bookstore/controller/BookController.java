package ru.alex.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alex.bookstore.service.BookService;
import ru.alex.bookstore.service.CategoryService;

@Controller("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService,
                          CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("topBooksByRating",bookService.findTopByRating(10));
        model.addAttribute("topBooksByCirculation",bookService.findTopByCirculation(5));
        model.addAttribute("categories",categoryService.findAll());
        return "books/index";
    }
}
