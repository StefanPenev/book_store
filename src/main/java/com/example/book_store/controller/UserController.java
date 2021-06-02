package com.example.book_store.controller;

import com.example.book_store.converter.UserConverter;
import com.example.book_store.dto.UserDto;
import com.example.book_store.exception.UserNotFoundException;
import com.example.book_store.model.User;
import com.example.book_store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        User user = userService.loadUserByUsername(username);
        return ResponseEntity.ok(userConverter.map(user));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Set<UserDto>> findAll() {
        Set<UserDto> userDtos = new HashSet<>();

        for (User user : userService.findAll()) {
            userDtos.add(userConverter.map(user));
        }

        return ResponseEntity.ok(userDtos);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        try {
            userService.deleteById(id);

            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {

            throw new UserNotFoundException(String.format("User with id: %s not found", id));
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid User user) {
        userService.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
