package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.UserBook;
import ru.alex.bookstore.dto.UserBookPreviewDto;

import java.util.List;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook,Integer> {
    @Query(
            value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM book
            LEFT JOIN user_book ON book.id = user_book.book_id
            ORDER BY rating DESC
            LIMIT :limit
            """,
            nativeQuery = true
    )
    List<UserBookPreviewDto> findTopByRating(Integer limit);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM book
            LEFT JOIN public.user_book ub on book.id = ub.book_id
            ORDER BY circulation DESC
            LIMIT :limit
            """,nativeQuery = true)
    List<UserBookPreviewDto> findTopByCirculation(Integer limit);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM book
            LEFT JOIN public.user_book ub on book.id = ub.book_id
            JOIN category ON category_id = category.id
            WHERE category.name = :category
            """,nativeQuery = true)
    List<UserBookPreviewDto> findAllByCategory(String category, Pageable pageable);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM book
            LEFT JOIN public.user_book ub on book.id = ub.book_id
            """,nativeQuery = true)
    List<UserBookPreviewDto> findAllBy(Pageable pageable);
}
