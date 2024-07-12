package ru.alex.bookstore.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.dto.UserBookReadDto;
import ru.alex.bookstore.integration.IntegrationTestBase;
import ru.alex.bookstore.service.UserBookService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserBookServiceIT extends IntegrationTestBase {

    private final UserBookService userBookService;

    @Autowired
    UserBookServiceIT(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @Test
    void findTopByRating(){
        List<UserBookPreviewDto> topBooksByRating = userBookService.findTopByRating(4);
        assertThat(topBooksByRating).hasSize(4);
        List<String> expectedAuthors = List.of("Leo Tolstoy","Fyodor Dostoevsky","Jane Austen","Charles Dickens");
        for(UserBookPreviewDto userBookPreviewDto: topBooksByRating){
            System.out.println(userBookPreviewDto.getIsInFavorites());
        }
        List<String> actualAuthors = topBooksByRating
                .stream()
                .map(UserBookPreviewDto::getAuthor)
                .toList();
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }

    @Test
    void findTopByCirculation(){
        List<UserBookPreviewDto> topBooksByCirculation = userBookService.findTopByCirculation(4);
        assertThat(topBooksByCirculation).hasSize(4);
        List<String> expectedAuthors = List.of("Thomas Hardy","George Eliot","Charles Dickens","Mark Twain");
        List<String> actualAuthors = topBooksByCirculation
                .stream()
                .map(UserBookPreviewDto::getAuthor)
                .toList();
        for(UserBookPreviewDto userBookPreviewDto: topBooksByCirculation){
            System.out.println(userBookPreviewDto.getIsInFavorites());
        }
        assertThat(actualAuthors).isEqualTo(expectedAuthors);
    }


    @Test
    void findById(){
        Optional<UserBookReadDto> bookOptional = userBookService.findById(7);
        assertThat(bookOptional).isPresent();
        UserBookReadDto book = bookOptional.get();
        assertThat(book.getName()).isEqualTo("Pride and Prejudice");
        assertThat(book.getAuthor()).isEqualTo("Jane Austen");
    }

    @Test
    void findFavorites(){
        Slice<UserBookPreviewDto> favoriteBooks = userBookService.findFavorites(7,Pageable.ofSize(3));
        assertThat(favoriteBooks).hasSize(3);
        List<String> expectedBookNames = List.of("Pride and Prejudice","Far from the Madding Crowd","War and Peace");
        List<String> actualBookNames = favoriteBooks
                .stream()
                .map(UserBookPreviewDto::getName)
                .toList();

        assertThat(actualBookNames).containsAll(expectedBookNames);
    }

    @Test
    void findInCart(){
        Slice<UserBookPreviewDto> booksInCart = userBookService.findInCart(7,Pageable.ofSize(3));
        assertThat(booksInCart).hasSize(3);
        List<String> expectedBookNames = List.of("War and Peace","Adventures of Huckleberry Finn","Pride and Prejudice");
        List<String> actualBookNames = booksInCart
                .stream()
                .map(UserBookPreviewDto::getName)
                .toList();

        assertThat(actualBookNames).containsAll(expectedBookNames);
    }


    @Test
    void addBookToFavorites(){
        userBookService.addBookToFavorites(7,7);
    }
}
