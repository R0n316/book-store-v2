package ru.alex.bookstore.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.repository.BookRepository;

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
}
