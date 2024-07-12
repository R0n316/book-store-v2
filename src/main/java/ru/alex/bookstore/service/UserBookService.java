package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.bookstore.database.entity.UserBook;
import ru.alex.bookstore.database.repository.UserBookRepository;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookReadDto;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserBookService {

    private final UserBookRepository userBookRepository;

    @Autowired
    public UserBookService(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    public List<UserBookPreviewDto> findTopByRating(Integer limit){
        return userBookRepository.findTopByRating(limit);
    }

    public List<UserBookPreviewDto> findTopByCirculation(Integer limit){
        return userBookRepository.findTopByCirculation(limit);
    }


    public Optional<UserBookReadDto> findById(Integer id){
        return userBookRepository.findBookById(id);
    }

    public Slice<UserBookPreviewDto> findFavorites(Integer userId,Pageable pageable){
        return userBookRepository.findFavorites(userId,pageable);
    }

    public Slice<UserBookPreviewDto> findInCart(Integer userId,Pageable pageable){
        return userBookRepository.findInCart(userId,pageable);
    }

    @Transactional
    public void addBookToFavorites(Integer bookId,Integer userId){
        Optional<UserBook> bookOptional = userBookRepository.findByBookIdAndUserId(bookId,userId);
        bookOptional.ifPresentOrElse(book -> {
            book.setInFavorites(true);
            userBookRepository.save(book);
        }, () -> userBookRepository.addUserBook(bookId,userId,true,false));

    }

    @Transactional
    public void deleteBookFromFavorites(Integer bookId, Integer userId){
        userBookRepository.deleteBookFromFavorites(bookId,userId);
    }

    @Transactional
    public void addBookToCart(Integer bookId, Integer userId){
        Optional<UserBook> bookOptional = userBookRepository.findByBookIdAndUserId(bookId,userId);
        bookOptional.ifPresentOrElse(book -> {
            book.setInCart(true);
            userBookRepository.save(book);
        }, () -> userBookRepository.addUserBook(bookId,userId,false,true));
    }

    @Transactional
    public void deleteBookFromCart(Integer bookId, Integer userId){
        userBookRepository.deleteBookFromCart(bookId,userId);
    }


    public Slice<QUserBookPreviewDto> findAllByFilter(BookFilter filter,Pageable pageable){
        return userBookRepository.findAllByFilter(filter,pageable);
    }
}
