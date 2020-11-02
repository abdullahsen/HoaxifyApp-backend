package com.abdullahsen.ws.auth;

import com.abdullahsen.ws.shared.GenericResponse;
import com.abdullahsen.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/api/1.0/auth")
    AuthResponse handleAuthentication(@RequestBody Credentials credentials){
        return authService.authenticate(credentials);
    }

    @PostMapping("/api/1.0/logout")
    GenericResponse handleLogout(@RequestHeader(name = "Authorization") String authorization){
        String token = authorization.substring(7);
        authService.clearToken(token);
        return new GenericResponse("Logout Success");
    }
}
