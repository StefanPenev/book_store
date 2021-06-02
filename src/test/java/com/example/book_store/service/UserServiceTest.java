package com.example.book_store.service;

import com.example.book_store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private RoleService roleService;

    @BeforeEach
    public void init() {
       userService = new UserService(userRepository, roleService);
    }

    @Test
    public void expectNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.deleteById(null);
        });
    }
}
