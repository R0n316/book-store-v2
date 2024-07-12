package ru.alex.bookstore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer>,BookRepositoryCustom {

    @Query("SELECT b.imagePath FROM Book b WHERE b.id = :id")
    Optional<String> getImagePathById(Integer id);

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC LIMIT :limit")
    List<Book> findTopByRating(Integer limit);

    @Query("SELECT b FROM Book b ORDER BY b.circulation DESC LIMIT :limit")
    List<Book> findTopByCirculation(Integer limit);

}