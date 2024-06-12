package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.repository.UserBookRepository;
import ru.alex.bookstore.dto.UserBookPreviewDto;

import java.util.List;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;

    @Autowired
    public UserBookService(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    // TODO после реализации функционала соединить все dto в одно целое
    public List<UserBookPreviewDto> findTopByRating(Integer limit){
        return userBookRepository.findTopByRating(limit);
    }

    public List<UserBookPreviewDto> findTopByCirculation(Integer limit){
        return userBookRepository.findTopByCirculation(limit);
    }

    public List<UserBookPreviewDto> findAllByCategory(String category,Pageable pageable){
        return userBookRepository.findAllByCategory(category,pageable);
    }

    public List<UserBookPreviewDto> findAllBy(Pageable pageable){
        return userBookRepository.findAllBy(pageable);
    }

}
