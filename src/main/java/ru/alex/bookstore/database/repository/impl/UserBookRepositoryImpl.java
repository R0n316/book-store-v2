package ru.alex.bookstore.database.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.querydsl.QPredicates;
import ru.alex.bookstore.database.repository.UserBookRepositoryCustom;
import ru.alex.bookstore.dto.BookFilter;
import ru.alex.bookstore.dto.QUserBookPreviewDto;

import static ru.alex.bookstore.database.entity.QBook.book;
import static ru.alex.bookstore.database.entity.QUserBook.userBook;


@Component
public class UserBookRepositoryImpl implements UserBookRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UserBookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<QUserBookPreviewDto> findAllByFilter(BookFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getName(), book.name::containsIgnoreCase)
                .add(filter.getCategory(), book.category.name::containsIgnoreCase)
                .build();

        var constructor = Projections.constructor(
                QUserBookPreviewDto.class,
                book.id,
                book.name,
                book.author,
                book.rating,
                book.price,
                userBook.user.id,
                userBook.isInFavorites,
                userBook.isInCart
        );

        var result = new JPAQuery<>(entityManager)
                .select(constructor)
                .distinct()
                .from(book)
                .leftJoin(userBook)
                .on(book.id.eq(userBook.book.id).and(userBook.user.id.eq(filter.getUserId())))
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = new JPAQuery<>(entityManager)
                .select(book.countDistinct())
                .from(book)
                .leftJoin(userBook)
                .on(book.id.eq(userBook.book.id).and(userBook.user.id.eq(filter.getUserId())))
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(result, pageable, total != null ? total : 0L);
    }

}
