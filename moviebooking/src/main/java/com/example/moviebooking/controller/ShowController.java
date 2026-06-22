package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.model.Show;
import com.example.moviebooking.service.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService service;

    public ShowController(ShowService service) {
        this.service = service;
    }

    @PostMapping
    public Show addShow(@RequestBody Show show) {
        return service.addShow(show);
    }

    @GetMapping
    public List<Show> getAllShows() {
        return service.getAllShows();
    }

    @GetMapping("/{id}")
    public Show getShowById(@PathVariable Long id) {
        return service.getShowById(id);
    }

    @PutMapping("/{id}")
    public Show updateShow(@PathVariable Long id,
                           @RequestBody Show show) {
        return service.updateShow(id, show);
    }

    @DeleteMapping("/{id}")
    public String deleteShow(@PathVariable Long id) {
        return service.deleteShow(id);
    }

    @GetMapping("/page")
    public Page<Show> getShows(
            @RequestParam int page,
            @RequestParam int size) {

        return service.getShows(page, size);
    }
}