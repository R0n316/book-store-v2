package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.bookstore.database.repository.BookReviewRepository;
import ru.alex.bookstore.dto.BookReviewSummaryDto;

@Service
@Transactional
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;

    @Autowired
    public BookReviewService(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Transactional(readOnly = true)
    public Slice<BookReviewSummaryDto> findAllByBook(Integer bookId,Integer userId, Pageable pageable){
        return bookReviewRepository.findAllByBook(bookId,userId,pageable);
    }

    @Transactional(readOnly = true)
    public Slice<BookReviewSummaryDto> findAllByBook(Integer bookId, Pageable pageable){
        return bookReviewRepository.findAllByBook(bookId,pageable);
    }

    public void deleteReview(Integer reviewId, Integer userId){
        bookReviewRepository.deleteReview(reviewId,userId);
    }
}
