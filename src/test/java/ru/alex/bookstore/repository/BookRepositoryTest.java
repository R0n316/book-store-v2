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
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.repository.BookRepository;
import ru.alex.bookstore.dto.BookFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@Sql("/sql/init-books.sql")
class BookRepositoryTest extends TestBase {

    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void getImagePathById(){
        Optional<String> imagePath = bookRepository.getImagePathById(3);
        assertThat(imagePath).isPresent();
        assertThat(imagePath.get()).isEqualTo("https://placeholder.com/150x200?text=A+Farewell+to+Arms");
    }

    @Test
    void findTopByRating(){
        List<Book> topBooksByRating = bookRepository.findTopByRating(3);

        assertThat(topBooksByRating).hasSize(3);

        List<Integer> expectedIds = List.of(1,2,8);
        List<Integer> actualIds = topBooksByRating
                .stream()
                .map(Book::getId)
                .toList();

        assertThat(actualIds).containsAll(expectedIds);
    }

    @Test
    void findTopByCirculation(){
        List<Book> topBooksByCirculation = bookRepository.findTopByCirculation(3);

        assertThat(topBooksByCirculation).hasSize(3);

        List<Integer> expectedIds = List.of(2,9,10);
        List<Integer> actualIds = topBooksByCirculation
                .stream()
                .map(Book::getId)
                .toList();

        assertThat(actualIds).containsAll(expectedIds);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindAllByFilterTest")
    void findAllByFilter(String name,String category,Integer pageNumber,Integer pageSize,
                         Integer expectedSize,List<Integer> expectedIds){
        BookFilter filter = new BookFilter(name,category,null);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Slice<Book> books = bookRepository.findAllByFilter(filter,pageable);
        assertThat(books).hasSize(expectedSize);

        List<Integer> actualIds = books.map(Book::getId).toList();

        assertThat(actualIds).isEqualTo(expectedIds);
    }

    static Stream<Arguments> getArgumentsForFindAllByFilterTest(){
        return Stream.of(
                Arguments.of(null,"Militants",0,2,2,List.of(3,4)),
                Arguments.of(null,null,0,15,10,List.of(1,2,3,4,5,6,7,8,9,10)),
                Arguments.of(null,"Detectives",0,2,2,List.of(2,9))
        );
    }
}