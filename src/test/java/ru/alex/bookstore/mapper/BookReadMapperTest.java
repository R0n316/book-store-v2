package ru.alex.bookstore.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookReadDto;

import static org.assertj.core.api.Assertions.*;

class BookReadMapperTest extends TestBase {
    private final BookReadMapper bookReadMapper;

    @Autowired
    BookReadMapperTest(BookReadMapper bookReadMapper) {
        this.bookReadMapper = bookReadMapper;
    }

    @Test
    void map(){
        Book book = Book.builder()
                .id(1)
                .name("test name")
                .author("test author")
                .rating(4F)
                .price(1000)
                .imagePath("test-image.png")
                .publisher("test publisher")
                .series("test series")
                .yearOfPublishing(2024)
                .isbn("0-3741-6245-X")
                .numberOfPages(120)
                .size("19.2x20.3")
                .coverType("soft")
                .circulation(100000000D)
                .weight(250)
                .ageRestrictions(16)
                .description("test description")
                .language("en")
                .build();

        BookReadDto actualResult = bookReadMapper.map(book);
        BookReadDto expectedResult = new BookReadDto(
          1,
          "test name",
          "test author",
          4F,
          1000,
          "test publisher",
          "test series",
          2024,
          "0-3741-6245-X",
          120,
          "19.2x20.3",
          "soft",
          100000000D,
          250,
          16,
          "test description"
        );

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
