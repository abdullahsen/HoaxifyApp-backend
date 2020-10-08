package com.abdullahsen.ws;

import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService){
		return args -> {
			User user = new User();
			user.setDisplayName("abdullahsen");
			user.setUsername("abdullahsen");
			user.setPassword("Password12");
			userService.save(user);
		};
	}
}
