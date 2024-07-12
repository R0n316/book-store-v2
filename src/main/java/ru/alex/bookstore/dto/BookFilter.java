package ru.alex.bookstore.dto;

//public record BookFilter(
//        String name,
//        String category,
//        Integer userId
//) {
//
//}


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