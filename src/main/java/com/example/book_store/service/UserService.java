package com.example.book_store.service;

import com.example.book_store.model.User;
import com.example.book_store.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Set<User> findAll() {
        return new HashSet<>(userRepository.findAll());
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("username %s does not exists", username)));
    }

    public void deleteById(@NonNull Long id) {

        userRepository.deleteById(id);
    }

    public void save(User user) {
        //Role role = roleService.findByAuthority("CLIENT");

        user.setCreatedAt(LocalDateTime.now());
        //user.setRoles(Set.of(role));
        userRepository.save(user);


    }
}
