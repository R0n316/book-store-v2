package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.bookstore.service.BookService;
import ru.alex.bookstore.service.CategoryService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/books")
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
        model.addAttribute("topBooksByRating",bookService.findTopByRating(8));
        model.addAttribute("topBooksByCirculation",bookService.findTopByCirculation(4));
        model.addAttribute("categories",categoryService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book",book);
                    return "books/book";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
