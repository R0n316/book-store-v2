package ru.alex.bookstore.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.entity.BookReview;
import ru.alex.bookstore.database.entity.User;
import ru.alex.bookstore.database.repository.BookRepository;
import ru.alex.bookstore.database.repository.UserRepository;
import ru.alex.bookstore.dto.ReviewCreateEditDto;

@Component
public class ReviewCreateEditMapper implements Mapper<ReviewCreateEditDto, BookReview>{
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewCreateEditMapper(BookRepository bookRepository,
                                  UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookReview map(ReviewCreateEditDto object) {
        Book book = bookRepository.findById(object.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findById(object.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookReview bookReview = new BookReview();
        bookReview.setId(object.getId());
        bookReview.setBook(book);
        bookReview.setUser(user);
        bookReview.setContent(object.getContent());
        return bookReview;
    }
}
