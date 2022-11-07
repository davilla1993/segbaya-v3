package com.dev.segbaya;

import com.dev.segbaya.domain.PublishHouse;
import com.dev.segbaya.domain.Role;
import com.dev.segbaya.domain.User;
import com.dev.segbaya.service.PublishHouseService;
import com.dev.segbaya.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;

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
	CommandLineRunner commandLineRunner(UserService userService, PublishHouseService publishHouseService){
		return args -> {

//			publishHouseService.savePublishHouse(new PublishHouse(null, "AWOUDI", "awoudi@gmail.com", "1234", "", "adresse", "awoudi.com"));
//			publishHouseService.savePublishHouse(new PublishHouse(null, "EDITI", "editi@gmail.com", "1234", "", "adresse", "editi.com"));
//			publishHouseService.savePublishHouse(new PublishHouse(null, "BON LIVRE", "bon@gmail.com", "1234", "", "adresse", "bon.com"));
//
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
			userService.saveUser(new User(null, "John", "Doe", "john@gmail.com","1234", "", new ArrayList<>(), null, null, null, null));
//			userService.saveUser(new User(null, "Will", "Smith", "will@gmail.com","1234", "", new ArrayList<>(),null));
			userService.saveUser(new User(null, "Abalo", "Koffi", "koffi@gmail.com","1234", "", new ArrayList<>(),null, null, null, null));
//			userService.saveUser(new User(null, "Nas", "Das", "nas@gmail.com","1234", "", new ArrayList<>(),null));

//			userService.addRoleToUser("john@gmail.com", "ROLE_USER");
//			userService.addRoleToUser("john@gmail.com", "ROLE_MANAGER");
//			userService.addRoleToUser("nas@gmail.com", "ROLE_USER");
//			userService.addRoleToUser("will@gmail.com", "ROLE_PUBLISH_HOUSE");
			userService.addRoleToUser("koffi@gmail.com", "ROLE_ADMIN");

		};
	}

}
