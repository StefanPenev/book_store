package com.example.book_store.controller;

import com.example.book_store.dto.LoginDto;
import com.example.book_store.dto.UserDto;
import com.example.book_store.service.LoginService;
import com.example.book_store.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {

        UserDto userDto = loginService.login(loginDto);
        String token = jwtUtil.generateToken(userDto);
        return ResponseEntity.ok(token);
    }
}