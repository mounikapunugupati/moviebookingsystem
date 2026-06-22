package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.service.SeatService;

@RestController
@RequestMapping("/seats")
@CrossOrigin(origins = "*")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/show/{showId}")
    public List<Seat> getSeatsByShow(@PathVariable Long showId) {

        return seatService.getSeatsByShow(showId);
    }

    @PutMapping("/book/{seatId}")
    public Seat bookSeat(@PathVariable Long seatId) {

        return seatService.bookSeat(seatId);
    }

    @PostMapping
    public Seat addSeat(@RequestBody Seat seat) {

        return seatService.saveSeat(seat);
    }
}