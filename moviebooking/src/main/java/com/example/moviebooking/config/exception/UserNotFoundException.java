package com.example.moviebooking.config.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super("user not found");
    }

    
}
