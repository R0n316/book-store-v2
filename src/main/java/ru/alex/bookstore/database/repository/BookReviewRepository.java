package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.BookReview;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview,Integer> {
    @Query("SELECT br FROM BookReview br WHERE br.book.id = :bookId")
    Slice<BookReview> findAllByBook(Integer bookId, Pageable pageable);
}
