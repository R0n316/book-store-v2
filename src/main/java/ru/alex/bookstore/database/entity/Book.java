package ru.alex.bookstore.database.entity;

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
    private String name;
    private String author;
    private Integer rating;
    private Integer price;

    @Column(name = "is_in_favorites")
    private boolean isInFavorites;

    @Column(name = "is_in_cart")
    private boolean isInCart;

    @Column(name = "image_path")
    private String imagePath;

    private String publisher;
    private String series;

    @Column(name = "year_of_publishing")
    private Integer yearOfPublishing;
    private String isbn;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;
    private String size;

    @Column(name = "cover_type")
    private String coverType;
    private Double circulation;
    private Integer weight;

    @Column(name = "age_restrictions")
    private Integer ageRestrictions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
