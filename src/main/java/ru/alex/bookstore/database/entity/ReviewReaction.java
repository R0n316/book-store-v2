package ru.alex.bookstore.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_reaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_review_id", referencedColumnName = "id")
    private BookReview bookReview;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;
}
