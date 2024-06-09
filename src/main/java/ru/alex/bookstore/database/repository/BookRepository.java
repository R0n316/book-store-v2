package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC LIMIT :limit")
    List<Book> findTopByRating(Integer limit);

    @Query("SELECT b FROM Book b ORDER BY b.circulation DESC LIMIT :limit")
    List<Book> findTopByCirculation(Integer limit);

    @Query("SELECT b FROM Book b JOIN FETCH b.category c WHERE c.name = :category")
    Slice<Book> findAllByCategory(String category, Pageable pageable);

    Slice<Book> findAllBy(Pageable pageable);
}