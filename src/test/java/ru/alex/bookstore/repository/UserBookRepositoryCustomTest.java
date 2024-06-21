package ru.alex.bookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.alex.bookstore.database.repository.UserBookRepository;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserBookRepositoryCustomTest {
    private final UserBookRepository userBookRepository;

    @Autowired
    UserBookRepositoryCustomTest(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    @Test
    void findAllByFilter(){
        BookFilter bookFilter = new BookFilter("me",null);
        Slice<QUserBookPreviewDto> books = userBookRepository.findAllByFilter(bookFilter, Pageable.ofSize(1));
        assertThat(books).hasSize(1);
    }
}
