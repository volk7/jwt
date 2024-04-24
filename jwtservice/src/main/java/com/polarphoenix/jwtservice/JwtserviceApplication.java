package com.polarphoenix.jwtservice;

import com.polarphoenix.jwtservice.entities.Role;
import com.polarphoenix.jwtservice.entities.User;
import com.polarphoenix.jwtservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtserviceApplication {

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(JwtserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			User adminAccount = userRepo.findByRole(Role.ADMIN);
			if (adminAccount == null) {
				User user = new User();
				user.setEmail("admin@gmail.com");
				user.setFirstname("admin");
				user.setLastname("admin");
				user.setRole(Role.ADMIN);
				user.setPassword(new BCryptPasswordEncoder().encode("password"));
				userRepo.save(user);
			}
		};
	}
}
