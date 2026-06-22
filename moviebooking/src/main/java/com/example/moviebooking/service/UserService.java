package com.example.moviebooking.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.moviebooking.config.JwtService;
import com.example.moviebooking.config.exception.UserNotFoundException;
import com.example.moviebooking.model.User;
import com.example.moviebooking.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UserService(UserRepository repository,
                       PasswordEncoder encoder,
                       JwtService jwtService) {

        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    // Register User
    public User addUser(User user) {

        String encodedPassword =
                encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return repository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Get User By Id
    public User getUserById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));
    }

    // Update User
    public User updateUser(Long id, User user) {

        User existingUser = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null &&
                !user.getPassword().isEmpty()) {

            existingUser.setPassword(
                    encoder.encode(user.getPassword())
            );
        }

        return repository.save(existingUser);
    }

    // Delete User
    public String deleteUser(Long id) {

        repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        repository.deleteById(id);

        return "User Deleted Successfully";
    }

    // Login
    public String login(String email, String password)
            throws UserNotFoundException {

        User user = repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        boolean matches =
                encoder.matches(password,
                        user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtService.generateToken(email);
    }
    // Get Profile
public User getProfile(Long id) {

    return repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("User Not Found"));
}

// Update Profile
public User getProfile(String email) {

    return repository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User Not Found"));
}
}