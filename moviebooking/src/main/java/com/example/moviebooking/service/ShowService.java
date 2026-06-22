package com.example.moviebooking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.repository.SeatRepository;
import com.example.moviebooking.repository.ShowRepository;

@Service
public class ShowService {

    private final ShowRepository repository;
    private final SeatRepository seatRepository;

    public ShowService(ShowRepository repository,
                       SeatRepository seatRepository) {
        this.repository = repository;
        this.seatRepository = seatRepository;
    }
    

   public Show addShow(Show show) {

    Show savedShow = repository.save(show);

    // Create 80 seats automatically
    for(char row = 'A'; row <= 'H'; row++) {

        for(int col = 1; col <= 10; col++) {

            Seat seat = new Seat();

            seat.setSeatNumber(row + String.valueOf(col));

            seat.setBooked(false);

            seat.setShow(savedShow);

            seatRepository.save(seat);
        }
    }

    return savedShow;
}

    public List<Show> getAllShows() {
        return repository.findAll();
    }

    public Show getShowById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Show Not Found"));
    }

    public Show updateShow(Long id, Show show) {

        Show existingShow = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Show Not Found"));

        existingShow.setMovie(show.getMovie());
        existingShow.setTheater(show.getTheater());
        existingShow.setShowTime(show.getShowTime());
        existingShow.setTicketPrice(show.getTicketPrice());

        return repository.save(existingShow);
    }

    public String deleteShow(Long id) {

        repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Show Not Found"));

        repository.deleteById(id);

        return "Show Deleted Successfully";
    }

    public Page<Show> getShows(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable);
    }
}