package ru.alex.bookstore.dto;

public interface UserBookReadDto {
    Integer getId();
    String getName();
    String getAuthor();
    Float getRating();
    Integer getPrice();
    String getPublisher();
    String getSeries();
    Integer getYearOfPublishing();
    String getIsbn();
    Integer getNumberOfPages();
    String getSize();
    String getCoverType();
    Double getCirculation();
    Integer getWeight();
    Integer getAgeRestrictions();
    String getDescription();
    Integer getUserId();
    Boolean getIsInFavorites();
}
