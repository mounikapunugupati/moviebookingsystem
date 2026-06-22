package com.example.moviebooking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import com.example.moviebooking.model.Movie;
import com.example.moviebooking.repository.MovieRepository;

@Service

public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Page<Movie> getMovies(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    return repository.findAll(pageable);
}
    public List<Movie> getAllMovies(){
        return repository.findAll();
    }
    public Movie addMovie(Movie movie){
        return repository.save(movie);
    }
    public String deleteMovie(Long id) {

    repository.findById(id).orElseThrow(() -> new RuntimeException("Movie Not Found"));

    repository.deleteById(id);

    return "Movie Deleted Successfully";
    }
    public Movie  updateMovie(Long id, Movie movie) {

    Movie existingMovie = repository.findById(id).orElseThrow(() -> new RuntimeException("Movie Not Found"));

    existingMovie.setTitle(movie.getTitle());
    existingMovie.setGenre(movie.getGenre());
    existingMovie.setLanguage(movie.getLanguage());
    existingMovie.setDuration(movie.getDuration());
    existingMovie.setRating(movie.getRating());
    existingMovie.setPlot(movie.getPlot());
    return repository.save(existingMovie);
}
public Movie getMovieById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie Not Found"));
}
    
}
