package ru.alex.bookstore.dto;


public record BookPreviewDto(
        Integer id,
        String name,
        String author,
        Float rating,
        Integer price
) {

}
