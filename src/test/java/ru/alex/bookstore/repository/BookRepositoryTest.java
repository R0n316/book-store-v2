package ru.alex.bookstore.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.repository.BookRepository;

import java.util.List;
import java.util.Optional;

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
}
