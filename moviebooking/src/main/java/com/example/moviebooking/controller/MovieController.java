package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.moviebooking.model.Movie;
import com.example.moviebooking.service.MovieService;

@RestController
@RequestMapping("/movies")

public class MovieController {
    private final MovieService service;
    public MovieController(MovieService service) {
        this.service = service;
    }
@GetMapping("/page")
public Page<Movie> getMovies(@RequestParam int page,@RequestParam int size) {

    return service.getMovies(page, size);
}

@GetMapping
public List<Movie> getAllMovies(){
    return service.getAllMovies();
    }
   @PostMapping
   public Movie addMovie(@RequestBody Movie movie) {
    return service.addMovie(movie);
   }
   @DeleteMapping("/{id}")
   public String  deleteMovie(@PathVariable Long id) {
    return service.deleteMovie(id);
   }
   @PutMapping("/{id}")
   public Movie updateMovie(@PathVariable Long id,
                         @RequestBody Movie movie) {

    return service.updateMovie(id, movie);
}
@GetMapping("/{id}")
public Movie getMovieById(@PathVariable Long id) {
    return service.getMovieById(id);
}
    
}
