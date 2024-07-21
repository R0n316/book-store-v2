package ru.alex.bookstore.dto;

public interface BookReviewSummaryDto {
    Integer getId();
    String getBookName();
    String getBookAuthor();
    Integer getUserId();
    String getUsername();
    String getContent();
    String getUserReaction();
    Integer getLikes();
    Integer getDislikes();
}
