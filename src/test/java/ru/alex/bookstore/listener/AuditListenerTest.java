package ru.alex.bookstore.listener;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.BookReview;
import ru.alex.bookstore.database.repository.BookReviewRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql","/sql/init-user-books.sql","/sql/init-book-reviews.sql"})
class AuditListenerTest extends TestBase {
    private final BookReviewRepository bookReviewRepository;
    private final EntityManager entityManager;

    @Autowired
    AuditListenerTest(BookReviewRepository bookReviewRepository,
                      EntityManager entityManager) {
        this.bookReviewRepository = bookReviewRepository;
        this.entityManager = entityManager;
    }

    @Test
    void checkCreatedAt(){
        BookReview review = new BookReview();
        review.setContent("test content");

        BookReview savedReview = bookReviewRepository.save(review);

        assertThat(savedReview.getCreatedAt()).isNotNull();
    }

    @Test
    void checkUpdatedAt(){
        Optional<BookReview> bookReviewOptional = bookReviewRepository.findById(1);

        assertThat(bookReviewOptional).isPresent();

        BookReview bookReview = bookReviewOptional.get();
        assertThat(bookReview.getUpdatedAt()).isNull();

        bookReview.setContent("new content");

        bookReviewRepository.save(bookReview);
        entityManager.flush();

        Optional<BookReview> savedReview = bookReviewRepository.findById(1);

        assertThat(savedReview).isPresent();
        savedReview.ifPresent(review -> assertThat(review.getUpdatedAt()).isNotNull());

    }
}
