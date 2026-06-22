package com.example.moviebooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moviebooking.model.User;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository <User ,Long>  {
    
    // finds the user by email
    Optional<User> findByEmail(String email);

    // search for user by email containing a query string
    List<User> findByEmailContaining(String query);

    // search the email with starting letters
    List<User> findByEmailStartingWith(String query);

    // search the email with ending letters
    List<User> findByEmailEndingWith(String query);

    // search by name
    List<User> findByNameContaining(String query);
    boolean existsByEmail(String email);

    
} 
