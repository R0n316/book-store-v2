package ru.alex.bookstore.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.dto.BookReviewSummaryDto;
import ru.alex.bookstore.service.BookReviewService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Sql({"/sql/init-books.sql","/sql/init-user-books.sql","/sql/init-book-reviews.sql"})
class BookReviewServiceIT extends TestBase {
    @Value("${app.page.size.reviews}")
    private Integer REVIEWS_SIZE;
    private final Integer BOOK_ID = 3;

    private final BookReviewService bookReviewService;

    @Autowired
    BookReviewServiceIT(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @Test
    void findAllByBook(){
        Pageable pageable = Pageable.ofSize(REVIEWS_SIZE);
        Slice<BookReviewSummaryDto> bookReviews = bookReviewService.findAllByBook(BOOK_ID, 1,pageable);
        assertThat(bookReviews).hasSize(2);
        // id, bookName, bookAuthor, username, content, likes, dislikes
        List<Object[]> expectedResult = List.of(
                new Object[]{
                       1,"A Farewell to Arms","Ernest Hemingway","alex",
                        "I thoroughly enjoyed this book." +
                            " The plot was engaging, and" +
                            " the characters were well-developed." +
                            " I found myself unable to put it down until " +
                            "I reached the very end.",
                        1,1
                },
                new Object[]{
                        4,"A Farewell to Arms","Ernest Hemingway","test",
                        "This book was a fantastic read! The mystery kept" +
                                " me guessing until the very end, and the" +
                                " twists and turns were expertly executed." +
                                " I highly recommend it to anyone who loves" +
                                " a good thriller.",
                        2,0
                }
        );

        List<Object[]> actualResult = bookReviews.map(
                review -> new Object[]{
                        review.getId(),
                        review.getBookName(),
                        review.getBookAuthor(),
                        review.getUsername(),
                        review.getContent(),
                        review.getLikes(),
                        review.getDislikes()
                }
        ).toList();

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}
