package com.example.moviebooking.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shows")
@Data
@NoArgsConstructor
public class Show {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theater theater;

    private LocalDateTime showTime;

    private Double ticketPrice;

    public Show(Movie movie, Theater theater, LocalDateTime showTime, Double ticketPrice) {
        this.movie = movie;
        this.theater = theater;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }
    
}
