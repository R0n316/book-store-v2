package ru.alex.bookstore.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.dto.BookReviewDto;
import ru.alex.bookstore.dto.BookReviewReadDto;
import ru.alex.bookstore.dto.UserReviewDto;
import ru.alex.bookstore.service.BookReviewService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Sql({"/sql/init-books.sql","/sql/init-user-books.sql","/sql/init-book-reviews.sql"})
class BookReviewServiceIT extends TestBase {
    @Value("${app.page.size.reviews}")
    private Integer REVIEWS_SIZE;
    private final Integer BOOK_ID = 7;

    private final BookReviewService bookReviewService;

    @Autowired
    BookReviewServiceIT(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    @Test
    void findAllByBook(){
        BookReviewDto bookReviewDto = new BookReviewDto(
                7,
                "Adventures of Huckleberry Finn",
                "Mark Twain"
        );
        UserReviewDto userReviewDto = new UserReviewDto(2,"test");
        BookReviewReadDto bookReviewReadDto = new BookReviewReadDto(
                2,
                bookReviewDto,
                userReviewDto,
                8,1
        );
        List<BookReviewReadDto> reviews = Collections.singletonList(bookReviewReadDto);
        Slice<BookReviewReadDto> expectedResult = new SliceImpl<>(reviews);
        Pageable pageable = Pageable.ofSize(REVIEWS_SIZE);
        Slice<BookReviewReadDto> actualResult = bookReviewService.findAllByBook(BOOK_ID,pageable);

        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}
