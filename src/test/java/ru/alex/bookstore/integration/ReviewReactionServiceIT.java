package ru.alex.bookstore.integration;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Reaction;
import ru.alex.bookstore.database.entity.ReviewReaction;
import ru.alex.bookstore.service.ReviewReactionService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql", "/sql/init-user-books.sql", "/sql/init-book-reviews.sql"})
class ReviewReactionServiceIT extends TestBase {

    private final Integer REVIEW_ID = 1;
    private final Integer USER_ID = 1;

    private final EntityManager entityManager;
    private final ReviewReactionService reviewReactionService;

    @Autowired
    ReviewReactionServiceIT(EntityManager entityManager,
                            ReviewReactionService reviewReactionService) {
        this.entityManager = entityManager;
        this.reviewReactionService = reviewReactionService;
    }

    @Test
    void respondViewWhenReviewReactionExists() {
        Optional<ReviewReaction> reviewReactionOptional = reviewReactionService.findByReviewAndUser(REVIEW_ID, USER_ID);
        assertThat(reviewReactionOptional).isPresent();
        ReviewReaction reaction = reviewReactionOptional.get();
        assertThat(reaction.getReaction()).isEqualTo(Reaction.LIKE);

        reviewReactionService.respondReview(Reaction.DISLIKE, REVIEW_ID, USER_ID);
        entityManager.refresh(reaction);
        assertThat(reaction.getReaction()).isEqualTo(Reaction.DISLIKE);
    }

    @Test
    void respondViewWhenReviewReactionNotExists(){
        Integer reviewId = 5;
        Integer userId = 2;
        Optional<ReviewReaction> reviewReactionOptional = reviewReactionService.findByReviewAndUser(reviewId,userId);

        assertThat(reviewReactionOptional).isEmpty();

        reviewReactionService.respondReview(Reaction.LIKE,reviewId,userId);
        reviewReactionOptional = reviewReactionService.findByReviewAndUser(reviewId,userId);
        assertThat(reviewReactionOptional).isPresent();
        ReviewReaction reaction = reviewReactionOptional.get();

        assertThat(reaction.getUser().getId()).isEqualTo(userId);
        assertThat(reaction.getBookReview().getId()).isEqualTo(reviewId);
        assertThat(reaction.getReaction()).isEqualTo(Reaction.LIKE);
    }
}