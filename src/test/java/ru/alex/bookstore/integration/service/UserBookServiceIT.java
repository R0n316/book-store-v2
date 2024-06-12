package ru.alex.bookstore.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.alex.bookstore.dto.UserBookPreviewDto;
import ru.alex.bookstore.integration.IntegrationTestBase;
import ru.alex.bookstore.service.UserBookService;

import java.util.List;

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
    void findAllByCategory(){
        String category = "Фэнтези";
        List<UserBookPreviewDto> booksByCategory = userBookService.findAllByCategory(category, PageRequest.of(0,3));
        assertThat(booksByCategory).hasSize(3);
        List<String> expectedAuthors = List.of("Jane Austen","Herman Melville","Emily Bronte");
        List<String> actualAuthors = booksByCategory
                .stream()
                .map(UserBookPreviewDto::getAuthor)
                .toList();

        assertThat(actualAuthors).containsAll(expectedAuthors);
    }

    @Test
    void findAllBy(){
        List<UserBookPreviewDto> books = userBookService.findAllBy(Pageable.ofSize(5));
        assertThat(books).hasSize(5);
    }
}
