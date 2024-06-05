package ru.alex.bookstore.dto;


public record BookPreviewDto(
        Integer id,
        String name,
        String author,
        Integer rating,
        Integer price,
        String imagePath,
        boolean isInFavorites,
        boolean isInCart
) {

}
