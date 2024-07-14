package ru.alex.bookstore.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookPreviewDto;

import static org.assertj.core.api.Assertions.*;

class BookPreviewMapperTest extends TestBase {
    private final BookPreviewMapper bookPreviewMapper;

    @Autowired
    BookPreviewMapperTest(BookPreviewMapper bookPreviewMapper) {
        this.bookPreviewMapper = bookPreviewMapper;
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

        BookPreviewDto actualResult = bookPreviewMapper.map(book);
        BookPreviewDto expectedResult = new BookPreviewDto(
                1,"test name","test author", 4F,1000
        );

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
