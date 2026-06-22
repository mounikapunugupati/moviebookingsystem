package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.model.Theater;
import com.example.moviebooking.service.TheaterService;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService service;

    public TheaterController(TheaterService service) {
        this.service = service;
    }

    @PostMapping
    public Theater addTheater(@RequestBody Theater theater) {
        return service.addTheater(theater);
    }

    @GetMapping
    public List<Theater> getAllTheaters() {
        return service.getAllTheaters();
    }

    @GetMapping("/{id}")
    public Theater getTheaterById(@PathVariable Long id) {
        return service.getTheaterById(id);
    }

    @PutMapping("/{id}")
    public Theater updateTheater(@PathVariable Long id,
                                 @RequestBody Theater theater) {
        return service.updateTheater(id, theater);
    }

    @DeleteMapping("/{id}")
    public String deleteTheater(@PathVariable Long id) {
        return service.deleteTheater(id);
    }

    @GetMapping("/page")
    public Page<Theater> getTheaters(
            @RequestParam int page,
            @RequestParam int size) {

        return service.getTheaters(page, size);
    }
}