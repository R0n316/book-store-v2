package ru.alex.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewCreateEditDto {
    private Integer id;
    private Integer bookId;
    private Integer userId;
    private String content;
}
