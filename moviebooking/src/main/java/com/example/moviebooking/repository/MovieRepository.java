package com.example.moviebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebooking.model.Movie;

@Repository

public interface MovieRepository extends JpaRepository<Movie,Long> {
    
}
