package ru.alex.bookstore.http.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.alex.bookstore.dto.UserDto;
import ru.alex.bookstore.service.UserBookService;

import java.util.function.BiConsumer;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/books")
public class UserBookRestController {

    private final UserBookService userBookService;

    @Autowired
    public UserBookRestController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @PatchMapping("/{bookId}/toFavorites")
    public ResponseEntity<HttpStatus> addBookToFavorites(@AuthenticationPrincipal UserDto user,
                                           @PathVariable("bookId") Integer bookId){
        return processRequest(userBookService::addBookToFavorites,user,bookId);
    }

    @PatchMapping("/{bookId}/fromFavorites")
    public ResponseEntity<HttpStatus> deleteBookFromFavorites(@AuthenticationPrincipal UserDto user,
                                                              @PathVariable("bookId") Integer bookId){
        return processRequest(userBookService::deleteBookFromFavorites,user,bookId);
    }

    @PatchMapping("/{bookId}/toCart")
    public ResponseEntity<HttpStatus> addBookToCart(@AuthenticationPrincipal UserDto user,
                                                         @PathVariable("bookId") Integer bookId){
        return processRequest(userBookService::addBookToCart,user,bookId);
    }

    @PatchMapping("/{bookId}/fromCart")
    public ResponseEntity<HttpStatus> deleteBookFromCart(@AuthenticationPrincipal UserDto user,
                                                              @PathVariable("bookId") Integer bookId){
        return processRequest(userBookService::deleteBookFromCart,user,bookId);
    }

    private ResponseEntity<HttpStatus> processRequest(BiConsumer<Integer,Integer> consumer, UserDto user,Integer bookId){
        if(user == null){
            return new ResponseEntity<>(FORBIDDEN);
        }
        consumer.accept(bookId, user.id());
        return new ResponseEntity<>(OK);
    }
}
