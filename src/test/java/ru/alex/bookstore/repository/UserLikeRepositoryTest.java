package ru.alex.bookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.repository.UserLikeRepository;
import ru.alex.bookstore.dto.BookReviewWithUserLikeDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql", "/sql/init-user-books.sql", "/sql/init-book-reviews.sql"})
class UserLikeRepositoryTest extends TestBase {
    @Value("${app.page.size.reviews}")
    private Integer REVIEWS_SIZE;

    private final UserLikeRepository userLikeRepository;

    @Autowired
    UserLikeRepositoryTest(UserLikeRepository userLikeRepository) {
        this.userLikeRepository = userLikeRepository;
    }

    @Test
    void findReviewsByBook(){
        Slice<BookReviewWithUserLikeDto> reviews = userLikeRepository.findReviewsByBook(3,1,Pageable.ofSize(REVIEWS_SIZE));

        List<Object[]> expectedObjects = List.of(
                new Object[]{1,1,"like",true},
                new Object[]{1,4,"like",true}
        );
        List<Object[]> actualObjects = reviews
                .map(review -> new Object[]{
                        review.getUserIdFromUserLike(),
                        review.getBookReviewId(),
                        review.getReaction(),
                        review.getIsReacted()
                })
                .toList();

        assertThat(reviews).hasSize(2);
        assertThat(actualObjects).usingRecursiveComparison().isEqualTo(expectedObjects);
    }
}