package ru.alex.bookstore.dto;

public interface UserBookPreviewDto{
    Integer getId();
    String getName();
    String getAuthor();
    Float getRating();
    Integer getPrice();
    Integer getUserId();
    Boolean getIsInFavorites();
    Boolean getIsInCart();
}
