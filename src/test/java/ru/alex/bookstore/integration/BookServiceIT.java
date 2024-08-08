package ru.alex.bookstore.integration;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.dto.*;
import ru.alex.bookstore.service.BookService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@Sql("/sql/init-books.sql")
class BookServiceIT extends TestBase {
    private final Integer BOOK_ID = 1;

    private final BookService bookService;
    private final CacheManager cacheManager;
    private final EntityManager entityManager;

    @Autowired
    BookServiceIT(BookService bookService, CacheManager cacheManager,
                  EntityManager entityManager) {
        this.bookService = bookService;
        this.cacheManager = cacheManager;
        this.entityManager = entityManager;
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForFindAllByFilterTest")
    void findAllByFilter(String name, String category, Integer pageNumber, Integer pageSize,
                         Integer expectedSize, List<BookPreviewDto> expectedBooks){
        BookFilter filter = new BookFilter(name,category,null);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Slice<BookPreviewDto> actualResult = bookService.findAllByFilter(filter,pageable);

        assertThat(actualResult).hasSize(expectedSize);
        assertThat(actualResult.getContent()).isEqualTo(expectedBooks);
    }


    static Stream<Arguments> getArgumentsForFindAllByFilterTest(){
        return Stream.of(
                Arguments.of(null,"Militants",0,2,2, List.of(
                        new BookPreviewDto(3,"A Farewell to Arms","Ernest Hemingway",3.88F,799),
                        new BookPreviewDto(4,"The Great Gatsby","F. Scott Fitzgerald",3.82F,899)
                )),
                Arguments.of(null,"Detectives",0,2,2,List.of(
                        new BookPreviewDto(2,"Harry Potter and the Philosopher's Stone","J.K. Rowling",4.44F,1200),
                        new BookPreviewDto(9,"One Hundred Years of Solitude","Gabriel Garcia Marquez",4.12F,999)
                ))
        );
    }

    @Test
    void findImageWhenImageExists() throws IOException {
        Optional<byte[]> actualImage = bookService.findImage(BOOK_ID);

        assertThat(actualImage).isPresent();

        byte[] expectedImage = Files.readAllBytes(Path.of("src/test/resources/static/images/test-image.jpg"));

        assertThat(actualImage.get()).isEqualTo(expectedImage);
    }

    @Test
    void findImageWhenImageNotExists(){
        Integer bookId = 2;
        Optional<byte[]> actualImage = bookService.findImage(bookId);

        assertThat(actualImage).isEmpty();
    }

    @Test
    void checkFindImageCaching(){
        Optional<byte[]> image = bookService.findImage(BOOK_ID);
        assertThat(image).isPresent();

        Cache cache = cacheManager.getCache("images");
        assertThat(cache).isNotNull();
        Cache.ValueWrapper valueWrapper = cache.get(BOOK_ID);
        assertThat(valueWrapper).isNotNull();
        byte[] cachedImageData = (byte[]) valueWrapper.get();
        assertThat(image.get()).isEqualTo(cachedImageData);
    }

    @Test
    void create() throws URISyntaxException, IOException {
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
                        "test-image.jpg",
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
        Integer maxId = entityManager.createQuery("SELECT MAX(b.id) FROM Book b",Integer.class).getSingleResult();
        BookPreviewDto actualResult = bookService.create(bookCreateEditDto);
        BookPreviewDto expectedResult = new BookPreviewDto(
                (maxId + 1),
                "test name",
                "test author",
                4F,
                1000
        );

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void findById(){
        BookReadDto expectedResult = new BookReadDto(
                1,
                "1984",
                "1984",
                4.17F,
                999,
                "Secker & Warburg",
                null,
                1949,
                "9780451524935",
                328,
                "Paperback",
                "Softcover",
                30000000D,
                198,
                0,
                null
        );

        Optional<BookReadDto> actualResult = bookService.findById(BOOK_ID);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(expectedResult);
    }
}
