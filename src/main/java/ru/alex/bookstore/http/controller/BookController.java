package ru.alex.bookstore.http.controller;

import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/books")
public class BookController {

//    private final BookService bookService;
//
//
//    @Autowired
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("topBooksByRating",bookService.findTopByRating(8));
//        model.addAttribute("topBooksByCirculation",bookService.findTopByCirculation(4));
//        return "books/index";
//    }

//    @GetMapping("/books/{id}")
//    public String findById(@PathVariable("id") Integer id, Model model){
//        return bookService.findById(id)
//                .map(book -> {
//                    model.addAttribute("book",book);
//                    return "books/book";
//                })
//                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
//    }

//    @GetMapping("/books")
//    public String findBookByCategory(@RequestParam(value = "category", required = false) String category,
//                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                     Model model){
//        Pageable pageable = PageRequest.of(page,8);
//        Slice<BookPreviewDto> slice = bookService.findAllByCategory(category, pageable);
//        model.addAttribute("books", PageResponse.of(slice));
//        return "books/books";
//    }
}
