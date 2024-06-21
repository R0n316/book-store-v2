package ru.alex.bookstore.dto;

public record QUserBookPreviewDto(
        Integer id,
        String name,
        String author,
        Float rating,
        Integer price,
        Integer userId,
        Boolean isInFavorites,
        Boolean isInCart
) {
}
