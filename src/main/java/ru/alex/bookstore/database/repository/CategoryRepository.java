package ru.alex.bookstore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.bookstore.database.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
