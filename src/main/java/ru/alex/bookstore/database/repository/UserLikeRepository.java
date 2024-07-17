package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.UserLike;
import ru.alex.bookstore.dto.BookReviewWithUserLikeDto;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {

    @Query(
            value = """
            SELECT book_review.*,
                   user_like.user_id AS userIdFromUserLike,
                   user_like.book_review_id AS bookReviewId,
                   user_like.reaction, user_like.is_reacted
            FROM book_review
            LEFT JOIN user_like ON book_review.id = user_like.book_review_id AND user_like.user_id = :userId
            WHERE book_review.book_id = :bookId
            """, nativeQuery = true
    )
    Slice<BookReviewWithUserLikeDto> findReviewsByBook(Integer bookId, Integer userId, Pageable pageable);
}

