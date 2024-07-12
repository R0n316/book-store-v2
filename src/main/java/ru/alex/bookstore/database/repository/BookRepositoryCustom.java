package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookFilter;

public interface BookRepositoryCustom {
    Slice<Book> findAllByFilter(BookFilter filter, Pageable pageable);
}
