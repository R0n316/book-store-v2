package ru.alex.bookstore.dto;

public record BookReadDto(
        Integer id,
        String name,
        String author,
        Float rating,
        Integer price,
        boolean isInFavorites,
        String imagePath,
        String publisher,
        String series,
        Integer yearOfPublishing,
        String isbn,
        Integer numberOfPages,
        String size,
        String coverType,
        Double circulation,
        Integer weight,
        Integer ageRestrictions,
        String description
) {
}
