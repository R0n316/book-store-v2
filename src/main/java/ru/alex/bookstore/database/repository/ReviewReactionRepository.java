package ru.alex.bookstore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.Reaction;
import ru.alex.bookstore.database.entity.ReviewReaction;

import java.util.Optional;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Integer> {

    @Query(value = "SELECT * FROM review_reaction r WHERE r.book_review_id = :reviewId AND r.user_id = :userId",nativeQuery = true)
    Optional<ReviewReaction> findByReviewAndUser(Integer reviewId, Integer userId);

    @Query("UPDATE ReviewReaction r SET r.reaction = :reaction WHERE r.user.id = :userId AND r.bookReview.id = :reviewId")
    @Modifying
    void updateReaction(Reaction reaction, Integer reviewId, Integer userId);

    @Query(value = """
            INSERT INTO review_reaction(user_id, book_review_id, reaction)
            VALUES (:userId, :reviewId, :reaction)
            """,nativeQuery = true)
    @Modifying
    void addReaction(String reaction,Integer reviewId,Integer userId);

    @Query(
            value = "UPDATE review_reaction SET reaction = null WHERE book_review_id = :reviewId AND user_id = :userId",
            nativeQuery = true
    )
    @Modifying
    void deleteReaction(Integer reviewId,Integer userId);
}

