package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookReviewDto;

@Component
public class BookReviewMapper implements Mapper<Book, BookReviewDto>{
    @Override
    public BookReviewDto map(Book object) {
        return new BookReviewDto(
                object.getId(),
                object.getName(),
                object.getAuthor()
        );
    }
}
