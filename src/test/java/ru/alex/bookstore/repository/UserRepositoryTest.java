package ru.alex.bookstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.alex.bookstore.TestBase;
import ru.alex.bookstore.database.entity.User;
import ru.alex.bookstore.database.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql("/sql/init-users.sql")
public class UserRepositoryTest extends TestBase {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findByUsernameWhenUserExists(){
        String username = "alex";

        Optional<User> user = userRepository.findByUsername(username);

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo(username);
    }

    @Test
    void findByUsernameWhenUserNotExists(){
        String username = "dummy";

        Optional<User> user = userRepository.findByUsername(username);

        assertThat(user).isEmpty();
    }
}
