package ru.alex.bookstore.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.alex.bookstore.database.entity.Role;
import ru.alex.bookstore.database.entity.User;
import ru.alex.bookstore.dto.UserCreateEditDto;

import java.util.Optional;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCreateEditMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();

        Optional.ofNullable(object.rawPassword())
                    .filter(StringUtils::hasText)
                    .map(passwordEncoder::encode)
                    .ifPresent(user::setPassword);
        user.setUsername(object.username());
        user.setRole(Role.USER);
        return user;
    }
}
