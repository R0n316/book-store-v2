package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.dto.BookReadDto;

@Component
public class BookReadMapper implements Mapper<Book, BookReadDto>{
    @Override
    public BookReadDto map(Book object) {
        return new BookReadDto(
                object.getId(),
                object.getName(),
                object.getAuthor(),
                object.getRating(),
                object.getPrice(),
                object.isInFavorites(),
                object.getImagePath(),
                object.getPublisher(),
                object.getSeries(),
                object.getYearOfPublishing(),
                object.getIsbn(),
                object.getNumberOfPages(),
                object.getSize(),
                object.getCoverType(),
                object.getCirculation(),
                object.getWeight(),
                object.getAgeRestrictions(),
                object.getDescription()
        );
    }
}
