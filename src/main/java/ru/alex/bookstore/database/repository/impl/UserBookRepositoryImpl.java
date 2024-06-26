package ru.alex.bookstore.database.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
    public Slice<QUserBookPreviewDto> findAllByFilter(BookFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.name(), book.name::containsIgnoreCase)
                .add(filter.category(), book.category.name::containsIgnoreCase)
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
                .from(book)
                .leftJoin(userBook).on(book.id.eq(userBook.book.id))
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = result.size();
        boolean hasNext = total > (long) (pageable.getPageNumber() + 1) * pageable.getPageSize();
        return new SliceImpl<>(result, pageable, hasNext);
    }
}
