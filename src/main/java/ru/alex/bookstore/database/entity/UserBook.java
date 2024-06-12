package ru.alex.bookstore.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_in_favorites")
    private boolean isInFavorites;

    @Column(name = "is_in_cart")
    private boolean isInCart;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
}
