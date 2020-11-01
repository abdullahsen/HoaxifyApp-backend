package com.abdullahsen.ws.auth;

import com.abdullahsen.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private UserVM user;

}
