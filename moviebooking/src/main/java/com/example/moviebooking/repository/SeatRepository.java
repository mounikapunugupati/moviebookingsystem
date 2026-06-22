package com.example.moviebooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviebooking.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByShowId(Long showId);
}