package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.entity.Category;
import ru.alex.bookstore.dto.BookCreateEditDto;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class BookCreateEditMapper implements Mapper<BookCreateEditDto, Book> {

    private final CategoryMapper categoryMapper;

    public BookCreateEditMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Book map(BookCreateEditDto object) {

        Book book = new Book();

        Optional.ofNullable(object.image())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> book.setImagePath(image.getOriginalFilename()));

//        Optional.ofNullable(object.category()).ifPresent(category ->
//                book.setCategory(categoryMapper.unmap(object.category())));

        Optional.ofNullable(object.category()).ifPresent(categoryDto -> {
            Category category = categoryMapper.unmap(object.category());
            book.setCategory(category);
        });

        book.setName(object.name());
        book.setAuthor(object.author());
        book.setRating(object.rating());
        book.setPrice(object.price());
        book.setPublisher(object.publisher());
        book.setSeries(object.series());
        book.setYearOfPublishing(object.yearOfPublishing());
        book.setIsbn(object.isbn());
        book.setNumberOfPages(object.numberOfPages());
        book.setSize(object.size());
        book.setCoverType(object.coverType());
        book.setCirculation(object.circulation());
        book.setWeight(object.weight());
        book.setAgeRestrictions(object.ageRestrictions());

        return book;
    }
}
