package ru.alex.bookstore.dto;

public interface BookReviewWithUserLikeDto {
    // Поля из book_review
    Integer getId();
    Integer getBookId();
    Integer getUserId();
    String getTitle();
    String getContent();
    Integer getRating();
    // Поля из user_like
    Integer getUserIdFromUserLike();
    Integer getBookReviewId();
    String getReaction();
    Boolean getIsReacted();
}
