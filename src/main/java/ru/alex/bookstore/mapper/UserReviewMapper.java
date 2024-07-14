package ru.alex.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.alex.bookstore.database.entity.User;
import ru.alex.bookstore.dto.UserReviewDto;

@Component
public class UserReviewMapper implements Mapper<User, UserReviewDto> {
    @Override
    public UserReviewDto map(User object) {
        return new UserReviewDto(
                object.getId(),
                object.getUsername()
        );
    }
}
