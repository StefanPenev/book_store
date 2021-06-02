package com.example.book_store.converter;

import com.example.book_store.dto.UserDto;
import com.example.book_store.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto map(User user) {

        return UserDto.builder()
                .username(user.getUsername())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .authorities(user.getAuthorities()
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.toSet()))
                .build();
    }

    public User map(UserDto userDto) {
        return User.builder()
                .build();
    }
}
