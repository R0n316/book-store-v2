package ru.alex.bookstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;
    private Integer rating;
    private Integer price;

    @Column(name = "is_in_favorites")
    private boolean isInFavorites;

    @Column(name = "is_in_cart")
    private boolean isInCart;
    private String imagePath;
}
