package com.example.moviebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebooking.model.Theater;

@Repository

public interface TheaterRepository extends JpaRepository<Theater,Long> {

    
} 