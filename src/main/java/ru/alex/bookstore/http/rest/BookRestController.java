package ru.alex.bookstore.http.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.bookstore.dto.BookCreateEditDto;
import ru.alex.bookstore.service.BookService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> addBook(@ModelAttribute BookCreateEditDto bookDto){
        bookService.create(bookDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable("id") Integer id){
        return bookService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
