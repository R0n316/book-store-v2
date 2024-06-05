package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.alex.bookstore.dto.BookPreviewDto;
import ru.alex.bookstore.entity.Book;

@Component
public class BookPreviewMapper implements Mapper<Book, BookPreviewDto> {
    @Override
    public BookPreviewDto map(Book object) {
        return new BookPreviewDto(
                object.getId(),
                object.getName(),
                object.getAuthor(),
                object.getRating(),
                object.getPrice(),
                object.getImagePath(),
                object.isInFavorites(),
                object.isInCart());
    }
}
