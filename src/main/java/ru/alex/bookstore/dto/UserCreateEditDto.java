package ru.alex.bookstore.dto;

public record UserCreateEditDto(
        String username,
        String rawPassword
) {
}
