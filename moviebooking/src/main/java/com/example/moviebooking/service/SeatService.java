package com.example.moviebooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.SeatRepository;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getSeatsByShow(Long showId) {
        return seatRepository.findByShowId(showId);
    }

    public Seat bookSeat(Long seatId) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if(seat.isBooked()) {
            throw new RuntimeException("Seat already booked");
        }

        seat.setBooked(true);

        return seatRepository.save(seat);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }
}