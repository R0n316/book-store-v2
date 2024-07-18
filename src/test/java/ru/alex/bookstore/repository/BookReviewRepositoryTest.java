package ru.alex.bookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.dto.BookReviewSummaryDto;
import ru.alex.bookstore.database.repository.BookReviewRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql","/sql/init-user-books.sql","/sql/init-book-reviews.sql"})
class BookReviewRepositoryTest extends TestBase {

    @Value("${app.page.size.reviews}")
    private Integer REVIEWS_SIZE;
    private final Integer BOOK_ID = 3;

    private final BookReviewRepository bookReviewRepository;
    @Autowired
    BookReviewRepositoryTest(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Test
    void findAllByBook(){
        Slice<BookReviewSummaryDto> reviews = bookReviewRepository.findAllByBook(BOOK_ID, Pageable.ofSize(REVIEWS_SIZE));

        assertThat(reviews).hasSize(2);

        List<Integer> expectedIds = List.of(1,4);
        List<Integer> actualIds = reviews
                .map(BookReviewSummaryDto::getId)
                .toList();
        Map<Integer,List<Integer>> expectedReactions = Map.of(
                1,List.of(1,1),
                4,List.of(2,0)
        );

        Map<Integer,List<Integer>> actualReactions = reviews
                .stream()
                .collect(Collectors.toMap(
                        BookReviewSummaryDto::getId,
                        it -> List.of(it.getLikes(),it.getDislikes())
                ));

        assertThat(actualIds).isEqualTo(expectedIds);
        assertThat(actualReactions).usingRecursiveComparison().isEqualTo(expectedReactions);
    }
}
