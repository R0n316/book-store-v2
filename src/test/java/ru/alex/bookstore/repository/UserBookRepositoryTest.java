package ru.alex.bookstore.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.UserBook;
import ru.alex.bookstore.database.repository.UserBookRepository;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookReadDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@Sql({"/sql/init-books.sql","/sql/init-users.sql","/sql/init-user-books.sql"})
class UserBookRepositoryTest extends TestBase {

    private final UserBookRepository userBookRepository;
    private final EntityManager entityManager;

    private static final Integer BOOK_ID = 2;
    private static final Integer USER_ID = 2;

    @Autowired
    public UserBookRepositoryTest(UserBookRepository userBookRepository,
                                  EntityManager entityManager) {
        this.userBookRepository = userBookRepository;
        this.entityManager = entityManager;
    }

    @Test
    void addUserBook(){
        int numberOfBooks = userBookRepository.findAll().size();
        Optional<UserBook> userBookOptional = userBookRepository.findById(numberOfBooks+1);
        assertThat(userBookOptional).isEmpty();
        userBookRepository.addUserBook(BOOK_ID+1,USER_ID,true,false);
        userBookOptional = userBookRepository.findById(numberOfBooks+1);
        assertThat(userBookOptional).isPresent();
        UserBook userBook = userBookOptional.get();
        assertThat(userBook.getBook().getId()).isEqualTo(BOOK_ID+1);
        assertThat(userBook.getUser().getId()).isEqualTo(USER_ID);
    }

    @Test
    void findTopByRating(){
        List<UserBookPreviewDto> topBooksByRating = userBookRepository.findTopByRating(USER_ID,3);
        assertThat(topBooksByRating).hasSize(3);
        List<String> expectedBookNames = List.of("1984","Harry Potter and the Philosopher's Stone","War and Peace");
        List<String> actualBookNames = topBooksByRating
                .stream()
                .map(UserBookPreviewDto::getName)
                .toList();
        assertThat(actualBookNames).containsAll(expectedBookNames);
    }

    @Test
    void findTopByCirculation(){
        List<UserBookPreviewDto> topBookByCirculation = userBookRepository.findTopByCirculation(USER_ID,2);
        assertThat(topBookByCirculation).hasSize(2);
        List<String> expectedBookNames = List.of(
                "Harry Potter and the Philosopher's Stone",
                "One Hundred Years of Solitude"
        );
        List<String> actualBookNames = topBookByCirculation
                .stream()
                .map(UserBookPreviewDto::getName)
                .toList();

        assertThat(actualBookNames).containsAll(expectedBookNames);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindAllByFilterTest")
    void findAllByFilter(String name,String category,Integer pageNumber,
                   Integer pageSize,Integer expectedSize,List<Integer> expectedIds){
        BookFilter filter = new BookFilter(name,category,USER_ID);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Slice<QUserBookPreviewDto> books = userBookRepository.findAllByFilter(filter,pageable);
        assertThat(books).hasSize(expectedSize);

        List<Integer> actualIds = books.map(QUserBookPreviewDto::id).toList();

        assertThat(actualIds).containsAll(expectedIds);
    }

    static Stream<Arguments> getArgumentsForFindAllByFilterTest(){
        return Stream.of(
                Arguments.of(null,"Militants",0,2,2,List.of(3,4)),
                Arguments.of(null,null,0,15,10,List.of(1,2,3,4,5,6,7,8,9,10)),
                Arguments.of(null,"Detectives",0,2,2,List.of(2,9))
        );
    }

    @Test
    void findBookById(){
        Optional<UserBookReadDto> bookOptional = userBookRepository.findBookById(BOOK_ID,USER_ID);
        assertThat(bookOptional).isPresent();
        UserBookReadDto book = bookOptional.get();
        assertThat(book.getName()).isEqualTo("Harry Potter and the Philosopher's Stone");
        assertThat(book.getAuthor()).isEqualTo("J.K. Rowling");
    }

    @Test
    void findFavorites(){
        Pageable pageable = PageRequest.of(0,3);
        Slice<UserBookPreviewDto> favoriteBooks = userBookRepository.findFavorites(USER_ID,pageable);
        List<Integer> expectedIds = List.of(2,4);
        List<Integer> actualIds = favoriteBooks
                .map(UserBookPreviewDto::getId)
                .toList();
        assertThat(favoriteBooks).hasSize(2);
        assertThat(expectedIds).containsAll(actualIds);
    }

    @Test
    void findInCart(){
        Pageable pageable = PageRequest.of(0,5);
        Slice<UserBookPreviewDto> booksInCart = userBookRepository.findInCart(USER_ID,pageable);

        assertThat(booksInCart).hasSize(1);

        List<Integer> expectedIds = List.of(2);
        List<Integer> actualIds = booksInCart
                .map(UserBookPreviewDto::getId)
                .toList();

        assertThat(actualIds).containsAll(expectedIds);
    }

    @Test
    void deleteBookFromFavorites() {
        Optional<UserBook> bookOptional = userBookRepository.findByBookIdAndUserId(USER_ID, BOOK_ID);
        assertThat(bookOptional).isPresent();
        UserBook book = bookOptional.get();

        assertThat(book.isInFavorites()).isTrue();
        userBookRepository.deleteBookFromFavorites(book.getBook().getId(), USER_ID);

        entityManager.refresh(book);

        assertThat(book.isInFavorites()).isFalse();
    }

    @Test
    void deleteBookFromCart(){
        Optional<UserBook> bookOptional = userBookRepository.findByBookIdAndUserId(USER_ID, BOOK_ID);
        assertThat(bookOptional).isPresent();
        UserBook book = bookOptional.get();

        assertThat(book.isInCart()).isTrue();
        userBookRepository.deleteBookFromCart(book.getBook().getId(), USER_ID);

        entityManager.refresh(book);

        assertThat(book.isInCart()).isFalse();
    }
}