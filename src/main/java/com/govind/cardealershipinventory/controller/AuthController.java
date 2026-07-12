package com.govind.cardealershipinventory.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govind.cardealershipinventory.dto.LoginRequest;
import com.govind.cardealershipinventory.dto.LoginResponse;
import com.govind.cardealershipinventory.entity.User;
import com.govind.cardealershipinventory.service.UserService;
import com.govind.cardealershipinventory.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtService.generateToken(user.getEmail());
        String role = userService.getRoleByEmail(request.getEmail());
        return new LoginResponse(token,role);
    }
    
}