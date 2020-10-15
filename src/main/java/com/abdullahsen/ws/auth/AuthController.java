package com.abdullahsen.ws.auth;

import com.abdullahsen.ws.shared.CurrentUser;
import com.abdullahsen.ws.user.User;
import com.abdullahsen.ws.user.UserRepository;
import com.abdullahsen.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/api/1.0/auth")
    UserVM handleAuthentication(@CurrentUser User user){
        return new UserVM(user);
    }
}
