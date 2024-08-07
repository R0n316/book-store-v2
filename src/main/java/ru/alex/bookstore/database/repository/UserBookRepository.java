package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.UserBook;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookReadDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepository extends
        JpaRepository<UserBook,Integer>,
        UserBookRepositoryCustom{

    Optional<UserBook> findByBookIdAndUserId(Integer userId, Integer bookId);

    @Modifying
    @Query(value = """
                INSERT INTO user_book(book_id,user_id,is_in_favorites,is_in_cart)
                VALUES (:bookId,:userId,:isInFavorites,:isInCart)""",
            nativeQuery = true)
    void addUserBook(Integer bookId,Integer userId,boolean isInFavorites,boolean isInCart);

    @Query(
            value = """
            SELECT book.*,is_in_favorites,is_in_cart,user_id
            FROM book
            LEFT JOIN user_book ON book.id = user_book.book_id AND user_id = :userId
            ORDER BY rating DESC
            LIMIT :limit
            """,
            nativeQuery = true
    )
    List<UserBookPreviewDto> findTopByRating(Integer userId,Integer limit);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart,user_id
            FROM book
            LEFT JOIN public.user_book ub on book.id = ub.book_id AND user_id = :userId
            ORDER BY circulation DESC
            LIMIT :limit
            """,nativeQuery = true)
    List<UserBookPreviewDto> findTopByCirculation(Integer userId,Integer limit);

    @Query(value = """
            SELECT book.*,is_in_favorites,user_id
            FROM book
            LEFT JOIN public.user_book ub on book.id = ub.book_id AND user_id = :userId
            WHERE book.id = :bookId
            """,nativeQuery = true)
    Optional<UserBookReadDto> findBookById(Integer bookId,Integer userId);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM user_book
            JOIN book ON user_book.book_id = book.id
            WHERE is_in_favorites AND user_id = :userId
            ORDER BY book.id
            """,nativeQuery = true)
    Page<UserBookPreviewDto> findFavorites(Integer userId, Pageable pageable);

    @Query(value = """
            SELECT book.*,is_in_favorites,is_in_cart
            FROM user_book
            JOIN book ON user_book.book_id = book.id
            WHERE is_in_cart AND user_id = :userId
            ORDER BY book.id
            """, nativeQuery = true)
    Page<UserBookPreviewDto> findInCart(Integer userId,Pageable pageable);


    @Modifying
    @Query("UPDATE UserBook ub SET ub.isInFavorites = false WHERE ub.book.id = :bookId AND ub.user.id = :userId")
    void deleteBookFromFavorites(Integer bookId, Integer userId);

    @Modifying
    @Query("UPDATE UserBook ub SET ub.isInCart = false WHERE ub.book.id = :bookId AND ub.user.id = :userId")
    void deleteBookFromCart(Integer bookId, Integer userId);


}
