package ru.alex.bookstore.dto;

public record BookReviewReadDto(
        Integer id,
        BookReviewDto book,
        UserReviewDto user,
        Integer likes,
        Integer dislikes
) {

}
