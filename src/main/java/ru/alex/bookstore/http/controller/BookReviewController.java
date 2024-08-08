package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alex.bookstore.dto.BookReviewSummaryDto;
import ru.alex.bookstore.dto.UserDto;
import ru.alex.bookstore.service.BookReviewService;
import ru.alex.bookstore.service.BookService;

import java.util.Map;

import static ru.alex.bookstore.util.PaginationUtils.getPageNumbers;

@Controller
@RequestMapping("/reviews")
public class BookReviewController {

    @Value("${app.page.size.reviews_page}")
    private Integer REVIEWS_SIZE;

    private final BookReviewService bookReviewService;
    private final BookService bookService;

    @Autowired
    public BookReviewController(BookReviewService bookReviewService,
                                BookService bookService) {
        this.bookReviewService = bookReviewService;
        this.bookService = bookService;
    }

    @GetMapping
    public String findAll(@RequestParam(value = "book", required = false) Integer bookId,
                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                            @AuthenticationPrincipal UserDto user,
                            Model model){
        Pageable pageable = PageRequest.of(page,REVIEWS_SIZE);
        Page<BookReviewSummaryDto> reviews;
        if(user != null){
            model.addAttribute("userId",user.id());
            if(bookId != null) {
                reviews = bookReviewService.findAllByBook(bookId,user.id(),pageable);
                bookService.findById(bookId).ifPresent(book -> model.addAttribute("book",book));
            } else {
                reviews = bookReviewService.findAll(user.id(), pageable);
            }
        } else {
            model.addAttribute("userId",null);
            if(bookId != null) {
                reviews = bookReviewService.findAllByBook(bookId,pageable);
                bookService.findById(bookId).ifPresent(book -> model.addAttribute("book",book));
            } else {
                reviews = bookReviewService.findAll(pageable);
            }
        }
        model.addAllAttributes(Map.of(
                "reviews",reviews,
                "currentPage",page,
                "pageNumbers", getPageNumbers(reviews.getTotalPages(),page)
        ));
        return "reviews/reviews";
    }
}
