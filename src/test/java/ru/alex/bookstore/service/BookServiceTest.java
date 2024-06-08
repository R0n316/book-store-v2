package ru.alex.bookstore.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    private final BookService bookService;
    private final CacheManager cacheManager;
    private static final Integer BOOK_ID = 14;
    @Autowired
    BookServiceTest(BookService bookService,
                    CacheManager cacheManager) {
        this.bookService = bookService;
        this.cacheManager = cacheManager;
    }

    @Test
    void testCaching(){
        Optional<byte[]> result = bookService.findImage(BOOK_ID);
        Optional<byte[]> result2 = bookService.findImage(BOOK_ID);
        assertEquals(result,result2);

        Objects.requireNonNull(cacheManager.getCache("images")).clear();

        Optional<byte[]> result3 = bookService.findImage(14);

        assertNotEquals(result,result3);
    }
}
