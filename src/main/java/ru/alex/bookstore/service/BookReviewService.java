package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.repository.BookReviewRepository;
import ru.alex.bookstore.dto.BookReviewReadDto;
import ru.alex.bookstore.mapper.BookReviewReadMapper;

@Service
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookReviewReadMapper bookReviewReadMapper;

    @Autowired
    public BookReviewService(BookReviewRepository bookReviewRepository,
                             BookReviewReadMapper bookReviewReadMapper) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookReviewReadMapper = bookReviewReadMapper;
    }

    public Slice<BookReviewReadDto> findAllByBook(Integer bookId, Pageable pageable){
        return bookReviewRepository.findAllByBook(bookId,pageable)
                .map(bookReviewReadMapper::map);
    }
}
