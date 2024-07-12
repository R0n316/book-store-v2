package ru.alex.bookstore.repository;

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@Sql({"/db/init-books.sql","/db/init-user-books.sql"})
class UserBookRepositoryTest extends TestBase {

    private final UserBookRepository userBookRepository;

    private static final Integer BOOK_ID = 2;
    private static final Integer USER_ID = 2;

    @Autowired
    public UserBookRepositoryTest(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    @Test
    void addUserBook(){
        Optional<UserBook> userBookOptional = userBookRepository.findById(6);
        assertThat(userBookOptional).isEmpty();
        userBookRepository.addUserBook(BOOK_ID,USER_ID,true,false);
        userBookOptional = userBookRepository.findById(6);
        assertThat(userBookOptional).isPresent();
        UserBook userBook = userBookOptional.get();
        assertThat(userBook.getBook().getId()).isEqualTo(BOOK_ID);
        assertThat(userBook.getUser().getId()).isEqualTo(USER_ID);
    }

    @Test
    void findTopByRating(){
        List<UserBookPreviewDto> topBooksByRating = userBookRepository.findTopByRating(3);
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
        List<UserBookPreviewDto> topBookByCirculation = userBookRepository.findTopByCirculation(2);
        assertThat(topBookByCirculation).hasSize(2);
        List<String> expectedBookNames = List.of(
                "Harry Potter and the Philosopher's Stone",
                "Don Quixote"
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
        List<Integer> actualIds = books.map(QUserBookPreviewDto::id).toList();

        assertThat(books).hasSize(expectedSize);
        assertThat(actualIds).containsAll(expectedIds);
    }

    static Stream<Arguments> getArgumentsForFindAllByFilterTest(){
        return Stream.of(
//                Arguments.of(null,"Militants",0,2,2,List.of(3,4)),
                Arguments.of(null,null,0,10,10,List.of(1,2,3,4,5,6,7,8,9,10)),
                Arguments.of(null,"Detectives",0,2,2,List.of(2,9))
        );
    }
}
