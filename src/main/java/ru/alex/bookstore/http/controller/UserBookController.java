package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.bookstore.dto.PageResponse;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.dto.UserDto;
import ru.alex.bookstore.service.UserBookService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class UserBookController {
    private final UserBookService userBookService;

    @Autowired
    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDto userDto, Model model){
        List<UserBookPreviewDto> topByRating = userBookService.findTopByRating(8);
        model.addAttribute("topBooksByRating", topByRating);
        List<UserBookPreviewDto> topByCirculation = userBookService.findTopByCirculation(4);
        model.addAttribute("topBooksByCirculation", topByCirculation);
        model.addAttribute("user",userDto);
        return "books/index";
    }

    @GetMapping("/books/{id}")
    public String findById(@AuthenticationPrincipal UserDto userDto,@PathVariable("id") Integer id, Model model){
        return userBookService.findById(id)
                .map(book -> {
                    model.addAttribute("book",book);
                    model.addAttribute("user",userDto);
                    return "books/book";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/books")
    public String findByCategory(@AuthenticationPrincipal UserDto userDto,
                                 @RequestParam(value = "category",required = false) String category,
                                 @RequestParam(value = "page",defaultValue = "0") Integer page,
                                 Model model){
        Pageable pageable = PageRequest.of(page,8);
        Slice<UserBookPreviewDto> slice = userBookService.findAllByCategory(category,pageable);
        model.addAttribute("user",userDto);
        model.addAttribute("books", PageResponse.of(slice));
        return "books/books";
    }

    @GetMapping("/books/favorites")
    public String favoriteBooks(@AuthenticationPrincipal UserDto userDto,
                                @RequestParam(value = "page",defaultValue = "0") Integer page,
                                Model model){
        model.addAttribute(
                "favoriteBooks",
                userBookService.findFavorites(userDto.id(),PageRequest.of(page,30))
        );
        return "books/favorites";
    }

    @GetMapping("/books/cart")
    public String booksInCart(@AuthenticationPrincipal UserDto userDto,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              Model model){
        model.addAttribute(
                "booksInCart",
                userBookService.findInCart(userDto.id(),PageRequest.of(page,30))
        );
        return "books/cart";
    }
}
