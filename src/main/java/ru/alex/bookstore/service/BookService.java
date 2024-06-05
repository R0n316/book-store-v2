package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.dto.BookPreviewDto;
import ru.alex.bookstore.mapper.BookPreviewMapper;
import ru.alex.bookstore.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookPreviewMapper bookPreviewMapper;

    @Autowired
    public BookService(BookRepository bookRepository,
                       BookPreviewMapper bookPreviewMapper) {
        this.bookRepository = bookRepository;
        this.bookPreviewMapper = bookPreviewMapper;
    }

    public List<BookPreviewDto> findTopByRating(Integer limit){
        return bookRepository.findTopByRating(limit).stream().map(bookPreviewMapper::map).toList();
    }

    public List<BookPreviewDto> findTopByCirculation(Integer limit){
        return bookRepository.findTopByCirculation(limit).stream().map(bookPreviewMapper::map).toList();
    }
}
