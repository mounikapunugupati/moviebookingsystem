package com.example.moviebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.moviebooking.model.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
}