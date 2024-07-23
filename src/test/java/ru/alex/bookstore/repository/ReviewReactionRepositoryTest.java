package ru.alex.bookstore.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Reaction;
import ru.alex.bookstore.database.entity.ReviewReaction;
import ru.alex.bookstore.database.repository.ReviewReactionRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql","/sql/init-users.sql","/sql/init-user-books.sql","/sql/init-book-reviews.sql"})
class ReviewReactionRepositoryTest extends TestBase {
    private final Integer REVIEW_ID = 1;
    private final Integer USER_ID = 1;

    private final ReviewReactionRepository reviewReactionRepository;
    private final EntityManager entityManager;

    @Autowired
    ReviewReactionRepositoryTest(ReviewReactionRepository reviewReactionRepository,
                                 EntityManager entityManager) {
        this.reviewReactionRepository = reviewReactionRepository;
        this.entityManager = entityManager;
    }

    @Test
    void findByReviewAndUser(){
        Optional<ReviewReaction> reviewReaction = reviewReactionRepository.findByReviewAndUser(REVIEW_ID, USER_ID);

        assertThat(reviewReaction).isPresent();
        assertThat(reviewReaction.get().getId()).isEqualTo(1);
    }

    @Test
    void updateReaction(){
        Integer reviewReactionId = 1;

        Optional<ReviewReaction> reviewReactionOptional = reviewReactionRepository.findById(reviewReactionId);

        assertThat(reviewReactionOptional).isPresent();
        ReviewReaction reaction = reviewReactionOptional.get();
        assertThat(reaction.getReaction()).isEqualTo(Reaction.LIKE);

        reviewReactionRepository.updateReaction(Reaction.DISLIKE,REVIEW_ID,USER_ID);

        entityManager.refresh(reaction);

        assertThat(reaction.getReaction()).isEqualTo(Reaction.DISLIKE);
    }

    @Test
    void addReaction(){
        Integer reviewId = 5;
        Integer userId = 2;
        Optional<ReviewReaction> reviewReaction = reviewReactionRepository.findByReviewAndUser(reviewId, userId);

        assertThat(reviewReaction).isEmpty();

        reviewReactionRepository.addReaction(Reaction.LIKE.name(), reviewId,userId);

        reviewReaction = reviewReactionRepository.findByReviewAndUser(reviewId, userId);

        assertThat(reviewReaction).isPresent();
    }

    @Test
    void deleteReaction(){
        Optional<ReviewReaction> reviewReaction = reviewReactionRepository.findByReviewAndUser(REVIEW_ID, USER_ID);

        assertThat(reviewReaction).isPresent();

        ReviewReaction reaction = reviewReaction.get();

        assertThat(reaction.getReaction()).isNotNull();

        reviewReactionRepository.deleteReaction(REVIEW_ID,USER_ID);
        entityManager.refresh(reaction);

        assertThat(reaction.getReaction()).isNull();
    }
}