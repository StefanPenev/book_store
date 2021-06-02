package com.example.book_store.service;

import com.example.book_store.model.Role;
import com.example.book_store.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    public void init() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void expectNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            roleService.findById(null);
        });
    }

    @Test
    public void expectIllegalArgumentException() {
        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(Role.builder().build()));

        roleService.findById(1L);

        verify(roleRepository, times(1)).findById(1L);
    }
}
