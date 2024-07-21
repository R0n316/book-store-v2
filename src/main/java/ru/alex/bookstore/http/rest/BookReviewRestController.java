package ru.alex.bookstore.http.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.bookstore.database.entity.Reaction;
import ru.alex.bookstore.dto.BookReviewReadDto;
import ru.alex.bookstore.dto.ReviewCreateEditDto;
import ru.alex.bookstore.dto.UserDto;
import ru.alex.bookstore.service.BookReviewService;
import ru.alex.bookstore.service.ReviewReactionService;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/reviews")
public class BookReviewRestController {

    private final ReviewReactionService reviewReactionService;
    private final BookReviewService bookReviewService;

    @Autowired
    public BookReviewRestController(ReviewReactionService reviewReactionService,
                                    BookReviewService bookReviewService) {
        this.reviewReactionService = reviewReactionService;
        this.bookReviewService = bookReviewService;
    }

    @PatchMapping("/{id}/respond")
    public ResponseEntity<HttpStatus> respondToReview(@PathVariable("id") Integer reviewId,
                                                      @AuthenticationPrincipal UserDto user,
                                                      @RequestBody String reaction){
        reviewReactionService.respondToReview(Reaction.valueOf(reaction),reviewId,user.id());
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") Integer reviewId,
                                                   @AuthenticationPrincipal UserDto user){
        bookReviewService.deleteReview(reviewId,user.id());
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity<BookReviewReadDto> saveReview(@RequestBody ReviewCreateEditDto review,
                                                        @AuthenticationPrincipal UserDto user){
        review.setUserId(user.id());
        BookReviewReadDto bookReviewReadDto = bookReviewService.saveReview(review);
        return new ResponseEntity<>(bookReviewReadDto, CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookReviewReadDto> editReview(@PathVariable("id") Integer id,
                                                 @AuthenticationPrincipal UserDto user,
                                                 @RequestBody ReviewCreateEditDto review
                                                 ){
        if(!review.getUserId().equals(user.id())){
            throw new ResponseStatusException(FORBIDDEN);
        }
        BookReviewReadDto bookReviewReadDto = bookReviewService.editReview(review, id);
        return new ResponseEntity<>(bookReviewReadDto,OK);
    }
}
