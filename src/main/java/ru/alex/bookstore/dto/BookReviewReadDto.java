package ru.alex.bookstore.dto;

public record BookReviewReadDto(
        Integer id,
        BookReviewDto book,
        UserReviewDto user,
        String content,
        Integer likes,
        Integer dislikes
) {

}
