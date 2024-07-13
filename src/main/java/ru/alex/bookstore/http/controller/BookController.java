package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.bookstore.dto.*;
import ru.alex.bookstore.service.BookService;
import ru.alex.bookstore.service.UserBookService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class BookController {

    private final BookService bookService;
    @Value("${app.page.size.top_by_rating_books}")
    private Integer TOP_BOOKS_BY_RATING_SIZE;

    @Value("${app.page.size.top_by_circulation_books}")
    private Integer TOP_BOOKS_BY_CIRCULATION_SIZE;

    @Value("${app.page.size.page_size}")
    private Integer PAGE_SIZE;

    private final UserBookService userBookService;

    @Autowired
    public BookController(UserBookService userBookService, BookService bookService) {
        this.userBookService = userBookService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDto user, Model model){
        if(user != null){
            List<UserBookPreviewDto> topByRating = userBookService.findTopByRating(user.id(),TOP_BOOKS_BY_RATING_SIZE);
            model.addAttribute("topBooksByRating", topByRating);
            List<UserBookPreviewDto> topByCirculation = userBookService.findTopByCirculation(user.id(),TOP_BOOKS_BY_CIRCULATION_SIZE);
            model.addAttribute("topBooksByCirculation", topByCirculation);
        } else{
            List<BookPreviewDto> topByRating = bookService.findTopByRating(TOP_BOOKS_BY_RATING_SIZE);
            model.addAttribute("topBooksByRating",topByRating);
            List<BookPreviewDto> topByCirculation = bookService.findTopByCirculation(TOP_BOOKS_BY_CIRCULATION_SIZE);
            model.addAttribute("topBooksByCirculation",topByCirculation);
        }
        model.addAttribute("user",user);
        return "books/index";
    }

    @GetMapping("/books/{id}")
    public String findById(@AuthenticationPrincipal UserDto user, @PathVariable("id") Integer id, Model model) {
        if(user != null){
            return userBookService.findById(id,user.id())
                    .map(book -> {
                        model.addAttribute("book",book);
                        model.addAttribute("user",user);
                        return "books/book";
                    }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        } else{
            return bookService.findById(id)
                    .map(book -> {
                        model.addAttribute("book",book);
                        model.addAttribute("user",null);
                        return "books/book";
                    }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        }
    }

    @GetMapping("/books")
    public String findByFilter(@AuthenticationPrincipal UserDto user,
                               @ModelAttribute BookFilter filter,
                               @RequestParam(value = "page",defaultValue = "0") Integer page,
                               Model model){
        Pageable pageable = PageRequest.of(page,PAGE_SIZE);
        if(user != null){
            filter.setUserId(user.id());
            model.addAttribute("books",PageResponse.of(userBookService.findAllByFilter(filter,pageable)));
        } else{
            model.addAttribute("books",PageResponse.of(bookService.findAllByFilter(filter,pageable)));
        }
        model.addAttribute("user",user);
        return "books/books";
    }

    @GetMapping("/books/favorites")
    public String favoriteBooks(@AuthenticationPrincipal UserDto user,
                                @RequestParam(value = "page",defaultValue = "0") Integer page,
                                Model model){
        model.addAttribute(
                "favoriteBooks",
                userBookService.findFavorites(user.id(),PageRequest.of(page,PAGE_SIZE))
        );
        return "books/favorites";
    }

    @GetMapping("/books/cart")
    public String booksInCart(@AuthenticationPrincipal UserDto user,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              Model model){
        model.addAttribute(
                "booksInCart",
                userBookService.findInCart(user.id(),PageRequest.of(page,PAGE_SIZE))
        );
        return "books/cart";
    }
}
