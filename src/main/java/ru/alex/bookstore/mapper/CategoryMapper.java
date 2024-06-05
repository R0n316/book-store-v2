package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.Category;
import ru.alex.bookstore.dto.CategoryDto;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    @Override
    public CategoryDto map(Category object) {
        return new CategoryDto(
                object.getId(),
                object.getName()
        );
    }
}
