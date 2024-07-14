package ru.alex.bookstore.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Category;
import ru.alex.bookstore.dto.CategoryDto;

import static org.assertj.core.api.Assertions.*;

class CategoryMapperTest extends TestBase {
    private final CategoryMapper categoryMapper;

    @Autowired
    CategoryMapperTest(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Test
    void map(){
        Category category = Category.builder()
                .id(1)
                .name("test category")
                .build();

        CategoryDto actualResult = categoryMapper.map(category);
        CategoryDto expectedResult = new CategoryDto(1,"test category");

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void unmap(){
        CategoryDto categoryDto = new CategoryDto(1,"test category");

        Category actualResult = categoryMapper.unmap(categoryDto);
        Category expectedResult = Category.builder()
                .id(1)
                .name("test category")
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
