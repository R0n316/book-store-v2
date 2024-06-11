package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.repository.UserRepository;
import ru.alex.bookstore.dto.UserCreateEditDto;
import ru.alex.bookstore.dto.UserDto;
import ru.alex.bookstore.mapper.UserCreateEditMapper;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserCreateEditMapper userCreateEditMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserCreateEditMapper userCreateEditMapper) {
        this.userRepository = userRepository;
        this.userCreateEditMapper = userCreateEditMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return userRepository.findByUsername(username)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        List.of(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    public void register(UserCreateEditDto userDto){
        userRepository.save(userCreateEditMapper.map(userDto));
    }
}
