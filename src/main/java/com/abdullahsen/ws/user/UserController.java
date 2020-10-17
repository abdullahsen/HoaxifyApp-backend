package com.abdullahsen.ws.user;

import com.abdullahsen.ws.error.ApiError;
import com.abdullahsen.ws.shared.CurrentUser;
import com.abdullahsen.ws.shared.GenericResponse;
import com.abdullahsen.ws.user.vm.UserUpdateVm;
import com.abdullahsen.ws.user.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/1.0")
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }

    @GetMapping("/users")
    Page<UserVM> getUsers(Pageable pageable, @CurrentUser User user) {
        return userService.getUsers(pageable, user).map(UserVM::new);
    }

    @GetMapping("/users/{username}")
    UserVM getUser(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new UserVM(user);
    }

    @PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
    UserVM updateUser(@RequestBody UserUpdateVm updatedUser, @PathVariable String username) {
        User user = userService.updateUser(username, updatedUser);
        return new UserVM(user);
    }


}
