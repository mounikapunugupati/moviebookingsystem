package com.example.moviebooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // A1, A2, B1

    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}