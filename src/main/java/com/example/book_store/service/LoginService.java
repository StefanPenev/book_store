package com.example.book_store.service;

import com.example.book_store.dto.LoginDto;
import com.example.book_store.dto.UserDto;
import com.example.book_store.model.Role;
import com.example.book_store.model.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public UserDto login(@NonNull LoginDto loginDto) {
        if (!StringUtils.hasText(loginDto.getUsername())
                || !StringUtils.hasText(loginDto.getPassword())) {
            throw new BadCredentialsException("Username or password should not be empty.");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = (User) authentication.getPrincipal();

        Set<Role> roles = user.getAuthorities();

        Set<String> authorities = roles.stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());

        return UserDto.builder()
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .authorities(authorities)
                .build();
    }
}
