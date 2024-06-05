package ru.alex.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC LIMIT :limit")
    List<Book> findTopByRating(Integer limit);

    @Query("SELECT b FROM Book b ORDER BY b.circulation DESC LIMIT :limit")
    List<Book> findTopByCirculation(Integer limit);
}