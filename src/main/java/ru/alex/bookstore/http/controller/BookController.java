package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.bookstore.dto.BookPreviewDto;
import ru.alex.bookstore.dto.PageResponse;
import ru.alex.bookstore.service.BookService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
//@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("topBooksByRating",bookService.findTopByRating(8));
        model.addAttribute("topBooksByCirculation",bookService.findTopByCirculation(4));
        return "books/index";
    }

    @GetMapping("/books/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book",book);
                    return "books/book";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/books")
    public String findBookByCategory(@RequestParam(value = "category", required = false) String category,
                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                     Model model){
        Pageable pageable = PageRequest.of(page,8);
        Slice<BookPreviewDto> slice = bookService.findAllByCategory(category, pageable);
        model.addAttribute("books", PageResponse.of(slice));
        return "books/books";
    }
}
