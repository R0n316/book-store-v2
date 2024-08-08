package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookFilter;

public interface BookRepositoryCustom {
    Page<Book> findAllByFilter(BookFilter filter, Pageable pageable);
}
