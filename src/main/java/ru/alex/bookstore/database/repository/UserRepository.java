package ru.alex.bookstore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.bookstore.database.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
