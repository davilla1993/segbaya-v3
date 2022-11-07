package com.dev.segbaya;

import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SegbayaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegbayaApplication.class, args);

	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {

			// Create roles
			Role role_user = new Role("ROLE_USER");
			Role role_admin = new Role("ROLE_ADMIN");

			// Create users
			User admin = new User("admin", "admin", "admin@gmail.com", "admin");
			User user = new User("user", "user", "user@gmail.com", "user");

			// Save roles
			userService.saveRole(role_admin);
			userService.saveRole(role_user);

			// Save users
			userService.saveUser(admin);
			userService.saveUser(user);

			// add roles to users
			userService.addRoleToUser("admin@gmail.com", "ROLE_ADMIN");
			userService.addRoleToUser("user@gmail.com", "ROLE_USER");






		};
	}

}
