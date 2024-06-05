package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.repository.CategoryRepository;
import ru.alex.bookstore.dto.CategoryDto;
import ru.alex.bookstore.mapper.CategoryMapper;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(categoryMapper::map).toList();
    }
}
