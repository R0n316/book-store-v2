package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;

public interface UserBookRepositoryCustom {
    Page<QUserBookPreviewDto> findAllByFilter(BookFilter filter, Pageable pageable);
}
