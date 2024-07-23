package ru.alex.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookFilter {
    private String name;
    private String category;
    private Integer userId;
}