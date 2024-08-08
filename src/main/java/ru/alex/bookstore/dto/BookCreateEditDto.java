package ru.alex.bookstore.dto;

import org.springframework.web.multipart.MultipartFile;

public record BookCreateEditDto(
        String name,
        String author,
        Float rating,
        Integer price,
        MultipartFile image,
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
        Integer categoryId
) {
}
