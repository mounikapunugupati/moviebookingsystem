package com.example.moviebooking.controller;

import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.config.exception.UserNotFoundException;
import com.example.moviebooking.dto.LoginRequest;
import com.example.moviebooking.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request)
            throws UserNotFoundException {

        return service.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}