package ru.alex.bookstore.dto;

public interface BookReviewSummaryDto {
    Integer getId();
    String getBookName();
    String getBookAuthor();
    String getUsername();
    String getContent();
    Integer getLikes();
    Integer getDislikes();
}
