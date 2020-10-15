package com.abdullahsen.ws.user;

import com.abdullahsen.ws.shared.CurrentUser;
import com.abdullahsen.ws.shared.GenericResponse;
import com.abdullahsen.ws.shared.Views;
import com.abdullahsen.ws.user.vm.UserVM;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/1.0/users")
	@ResponseStatus(value = HttpStatus.CREATED)
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("user created");
	}

	@GetMapping("/api/1.0/users")
	Page<UserVM> getUsers(Pageable pageable, @CurrentUser User user) {
		return userService.getUsers(pageable, user).map(UserVM::new);
	}

	
}
