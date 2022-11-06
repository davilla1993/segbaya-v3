package com.dev.segbaya;

import com.dev.segbaya.domain.Role;
import com.dev.segbaya.service.PublishHouseService;
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

			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_ADMIN"));

		};
	}

}
