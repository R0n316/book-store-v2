package ru.alex.bookstore.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.BookReview;
import ru.alex.bookstore.dto.BookReviewReadDto;

@Component
public class BookReviewReadMapper implements Mapper<BookReview, BookReviewReadDto>{
    private final BookReviewMapper bookReviewMapper;
    private final UserReviewMapper userReviewMapper;

    @Autowired
    public BookReviewReadMapper(BookReviewMapper bookReviewMapper,
                                UserReviewMapper userReviewMapper) {
        this.bookReviewMapper = bookReviewMapper;
        this.userReviewMapper = userReviewMapper;
    }

    @Override
    public BookReviewReadDto map(BookReview object) {
        return new BookReviewReadDto(
                object.getId(),
                bookReviewMapper.map(object.getBook()),
                userReviewMapper.map(object.getUser()),
                object.getLikes(),
                object.getDislikes()
        );
    }
}
