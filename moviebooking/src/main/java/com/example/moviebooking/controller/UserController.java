package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.model.User;
import com.example.moviebooking.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @GetMapping("/profile/{id}")
    public User getProfile(@PathVariable Long id) {
        return service.getProfile(id);
    }

    @GetMapping("/current-profile")
    public User getCurrentProfile() {
        // Get current authenticated user email from JWT
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        return service.getProfile(email);
    }
}