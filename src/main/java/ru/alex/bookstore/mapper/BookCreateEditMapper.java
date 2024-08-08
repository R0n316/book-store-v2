package ru.alex.bookstore.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.alex.bookstore.database.entity.Book;
import ru.alex.bookstore.database.entity.Category;
import ru.alex.bookstore.database.repository.CategoryRepository;
import ru.alex.bookstore.dto.BookCreateEditDto;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class BookCreateEditMapper implements Mapper<BookCreateEditDto, Book> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public BookCreateEditMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Book map(BookCreateEditDto object) {

        Book book = new Book();

        Optional.ofNullable(object.image())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> book.setImagePath(image.getOriginalFilename()));

        Optional<Category> byId = categoryRepository.findById(object.categoryId());
        byId.ifPresentOrElse(
                book::setCategory,
                () -> { throw new EntityNotFoundException("category not found"); }
        );

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
