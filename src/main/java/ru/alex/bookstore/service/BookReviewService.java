package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.repository.BookReviewRepository;
import ru.alex.bookstore.dto.BookReviewSummaryDto;

@Service
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;

    @Autowired
    public BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    public Slice<BookReviewSummaryDto> findAllByBook(Integer bookId, Pageable pageable){
        return bookReviewRepository.findAllByBook(bookId,pageable);
    }
}
