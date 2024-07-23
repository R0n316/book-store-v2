package ru.alex.bookstore.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        Slice<BookReviewSummaryDto> bookReviews;
        if(user != null){
            if(bookId != null){
                bookReviews = bookReviewService.findAllByBook(user.id(),bookId,pageable);
                bookService.findById(bookId).ifPresent(book -> model.addAttribute("book",book));
            } else{
                bookReviews = bookReviewService.findAll(user.id(), pageable);
            }
        } else{
            if(bookId != null){
                bookReviews = bookReviewService.findAllByBook(bookId,pageable);
                bookService.findById(bookId).ifPresent(book -> model.addAttribute("book",book));
            } else{
                bookReviews = bookReviewService.findAll(pageable);
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("reviews", bookReviews);
        return "reviews/reviews";
    }
}
