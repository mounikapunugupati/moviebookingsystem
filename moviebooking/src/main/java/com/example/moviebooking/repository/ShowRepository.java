package com.example.moviebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebooking.model.Show;

@Repository

public interface ShowRepository extends  JpaRepository<Show,Long>{

    
}
