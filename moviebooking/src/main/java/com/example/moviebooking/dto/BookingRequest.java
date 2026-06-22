package com.example.moviebooking.dto;

import lombok.Data;

@Data
public class BookingRequest {

    private Long showId;
    private Integer seatsBooked;

    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
}