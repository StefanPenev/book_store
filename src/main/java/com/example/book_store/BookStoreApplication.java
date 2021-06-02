package com.example.book_store;

import com.example.book_store.model.Role;
import com.example.book_store.model.User;
import com.example.book_store.service.RoleService;
import com.example.book_store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
public class BookStoreApplication {

	private UserService userService;
	private RoleService roleService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public BookStoreApplication(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.roleService = roleService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@PostConstruct
	public void execute() {

		Role role = Role.builder()
				.authority("ROLE_ADMIN")
				.build();

		roleService.save(role);

		User user = User.builder()
				.username("Stefan")
				.password(bCryptPasswordEncoder.encode("1234"))
				.age(18)
				.roles(Set.of(role))
				.build();

		userService.save(user);

		Role r = Role.builder()
				.authority("ROLE_CLIENT")
				.build();

		roleService.save(r);

		User u = User.builder()
				.username("Ivan")
				.password(bCryptPasswordEncoder.encode("1234"))
				.age(20)
				.roles(Set.of(r))
				.build();

		userService.save(u);
	}
}
