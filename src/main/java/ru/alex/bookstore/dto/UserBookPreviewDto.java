package ru.alex.bookstore.dto;

public interface UserBookPreviewDto{
    Integer getId();
    String getName();
    String getAuthor();
    Float getRating();
    Integer getPrice();
    Boolean getIsInFavorites();
    Boolean getIsInCart();
}
