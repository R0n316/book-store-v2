package ru.alex.bookstore.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;

public interface BookRepositoryCustom {
    Slice<QUserBookPreviewDto> findAllByFilter(BookFilter filter, Pageable pageable);
}
