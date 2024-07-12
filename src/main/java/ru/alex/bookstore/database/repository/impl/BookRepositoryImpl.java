package ru.alex.bookstore.database.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.querydsl.QPredicates;
import ru.alex.bookstore.database.repository.BookRepositoryCustom;
import ru.alex.bookstore.dto.BookFilter;

import static ru.alex.bookstore.database.entity.QBook.book;

@Component
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Slice<Book> findAllByFilter(BookFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filter.getName(), book.name::containsIgnoreCase)
                .add(filter.getCategory(), book.category.name::containsIgnoreCase)
                .build();

        var result = new JPAQuery<>(entityManager)
                .select(book)
                .distinct()
                .from(book)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = result.size();
        boolean hasNext = total > (long) (pageable.getPageNumber() + 1) * pageable.getPageSize();
        return new SliceImpl<>(result, pageable, hasNext);
    }
}
