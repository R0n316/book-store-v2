package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.BookReview;
import ru.alex.bookstore.dto.BookReviewSummaryDto;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview,Integer> {
    @Query(value = """
            SELECT br.id, b.name AS bookName, b.author AS bookAuthor, br.content,
                           u.id AS userId, u.username AS username, rr.reaction,
                           COUNT(CASE WHEN r.reaction = 'LIKE' THEN 1 END) AS likes,
                           COUNT(CASE WHEN r.reaction = 'DISLIKE' THEN 1 END) AS dislikes,
                           user_reaction.reaction AS userReaction
                    FROM book_review br
                    INNER JOIN book b ON b.id = br.book_id
                    INNER JOIN users u ON u.id = br.user_id
                    LEFT JOIN LATERAL (
                      SELECT r.reaction
                      FROM review_reaction r
                      WHERE r.book_review_id = br.id
                      LIMIT 1
                    ) rr ON TRUE
                    LEFT JOIN review_reaction r ON br.id = r.book_review_id
                    LEFT JOIN review_reaction user_reaction ON br.id = user_reaction.book_review_id AND user_reaction.user_id = :userId
                    WHERE br.book_id = :bookId
                    GROUP BY br.id, b.name, b.author, br.content, u.id, u.username, rr.reaction, user_reaction.reaction
            """,nativeQuery = true)
    Slice<BookReviewSummaryDto> findAllByBook(Integer bookId, Integer userId, Pageable pageable);

    @Query(value = """
            SELECT br.id, b.name AS bookName, b.author AS bookAuthor, br.content, u.username AS username,
                   COUNT(CASE WHEN r.reaction = 'LIKE' THEN 1 END) AS likes,
                   COUNT(CASE WHEN r.reaction = 'DISLIKE' THEN 1 END) AS dislikes
            FROM book_review br
            LEFT JOIN review_reaction r ON br.id = r.book_review_id
            INNER JOIN book b ON b.id = br.book_id
            INNER JOIN users u ON u.id = br.user_id
            WHERE br.book_id = :bookId
            GROUP BY br.id,b.name, br.id, b.author, u.username
            """,nativeQuery = true)
    Slice<BookReviewSummaryDto> findAllByBook(Integer bookId, Pageable pageable);

    @Query(value = "DELETE FROM book_review WHERE id = :reviewId AND user_id = :userId",nativeQuery = true)
    @Modifying
    void deleteReview(Integer reviewId, Integer userId);

//    @Query(
//            value = "INSERT INTO book_review(book_id, user_id, content) VALUES (:bookId,:userId,:content)",
//            nativeQuery = true
//    )
//    @Modifying
//    void saveReview(Integer bookId, Integer userId,String content);
}
