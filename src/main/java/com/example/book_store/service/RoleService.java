package com.example.book_store.service;

import com.example.book_store.model.Role;
import com.example.book_store.repository.RoleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Role findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority)
                .orElseThrow(IllegalAccessError::new);
    }

    public Role findById(@NonNull Long id) {
        return roleRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
