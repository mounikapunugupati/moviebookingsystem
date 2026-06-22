package com.example.moviebooking.service;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.moviebooking.model.Theater;
import com.example.moviebooking.repository.TheaterRepository;

@Service
public class TheaterService {

    private final TheaterRepository repository;

    public TheaterService(TheaterRepository repository) {
        this.repository = repository;
    }

    public Page<Theater> getTheaters(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable);
    }

    public List<Theater> getAllTheaters() {
        return repository.findAll();
    }

    public Theater addTheater(Theater theater) {
        return repository.save(theater);
    }

    public Theater getTheaterById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater Not Found"));
    }

    public Theater updateTheater(Long id, Theater theater) {

        Theater existingTheater = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater Not Found"));

        existingTheater.setName(theater.getName());
        existingTheater.setLocation(theater.getLocation());

        return repository.save(existingTheater);
    }

    public String deleteTheater(Long id) {

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater Not Found"));

        repository.deleteById(id);

        return "Theater Deleted Successfully";
    }
}