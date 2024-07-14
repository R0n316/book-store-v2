package ru.alex.bookstore.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.Role;
import ru.alex.bookstore.database.entity.User;
import ru.alex.bookstore.dto.UserCreateEditDto;

import static org.assertj.core.api.Assertions.*;

class UserCreateEditMapperTest extends TestBase {
    private final UserCreateEditMapper userCreateEditMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserCreateEditMapperTest(UserCreateEditMapper userCreateEditMapper,
                             PasswordEncoder passwordEncoder) {
        this.userCreateEditMapper = userCreateEditMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void map(){
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto("test username","123");
        String encodedPassword = passwordEncoder.encode("123");
        User expectedResult = new User(null,"test username",encodedPassword, Role.USER);
        User actualResult = userCreateEditMapper.map(userCreateEditDto);
        assertThat(actualResult.getId()).isEqualTo(expectedResult.getId());
        assertThat(actualResult.getUsername()).isEqualTo(expectedResult.getUsername());
        assertThat(actualResult.getRole()).isEqualTo(expectedResult.getRole());
        assertThat(passwordEncoder.matches("123", actualResult.getPassword())).isTrue();
    }
}