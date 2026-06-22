package com.example.moviebooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moviebooking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // Find all bookings for a specific user
    List<Booking> findByUserId(Long userId);
}