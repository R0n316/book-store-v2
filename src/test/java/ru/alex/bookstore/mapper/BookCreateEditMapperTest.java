package ru.alex.bookstore.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.entity.Category;
import ru.alex.bookstore.dto.BookCreateEditDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Sql("/sql/init-books.sql")
class BookCreateEditMapperTest extends TestBase {

    private final BookCreateEditMapper bookCreateEditMapper;

    @Autowired
    BookCreateEditMapperTest(BookCreateEditMapper bookCreateEditMapper) {
        this.bookCreateEditMapper = bookCreateEditMapper;
    }

    @Test
    void map() throws IOException, URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("static/images/test-image.jpg");
        assert resource != null;
        Path path = Paths.get(resource.toURI());
        byte[] image = Files.readAllBytes(path);
        BookCreateEditDto bookCreateEditDto = new BookCreateEditDto(
                "test name",
                "test author",
                4F,
                1000,
                new MockMultipartFile("test-image",
                        "static/images/test-image.jpg",
                        "image/jpeg", image),
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
                1
        );
        Category category = Category.builder()
                .id(1)
                .name("Fantasy")
                .build();
        Book actualResult = bookCreateEditMapper.map(bookCreateEditDto);

        Book expectedResult = Book.builder()
                .name("test name")
                .author("test author")
                .rating(4F)
                .price(1000)
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
                .imagePath("static/images/test-image.jpg")
                .category(category)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
