package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.bookstore.database.entity.BookReview;
import ru.alex.bookstore.database.repository.BookReviewRepository;
import ru.alex.bookstore.dto.BookReviewReadDto;
import ru.alex.bookstore.dto.BookReviewSummaryDto;
import ru.alex.bookstore.dto.ReviewCreateDto;
import ru.alex.bookstore.mapper.BookReviewReadMapper;
import ru.alex.bookstore.mapper.ReviewCreateEditMapper;

@Service
@Transactional
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final ReviewCreateEditMapper reviewCreateEditMapper;
    private final BookReviewReadMapper bookReviewReadMapper;

    @Autowired
    public BookReviewService(BookReviewRepository bookReviewRepository,
                             ReviewCreateEditMapper reviewCreateEditMapper,
                             BookReviewReadMapper bookReviewReadMapper) {
        this.bookReviewRepository = bookReviewRepository;
        this.reviewCreateEditMapper = reviewCreateEditMapper;
        this.bookReviewReadMapper = bookReviewReadMapper;
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

//    public void saveReview(ReviewCreateDto review){
//        bookReviewRepository.saveReview(
//                review.getBookId(),
//                review.getUserId(),
//                review.getContent()
//        );
//    }

    public BookReviewReadDto saveReview(ReviewCreateDto review){
        BookReview savedReview = bookReviewRepository.save(reviewCreateEditMapper.map(review));
        return bookReviewReadMapper.map(savedReview);
    }
}
