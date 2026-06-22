package com.example.moviebooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.dto.BookingRequest;
import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.User;
import com.example.moviebooking.model.Show;
import com.example.moviebooking.repository.UserRepository;
import com.example.moviebooking.repository.ShowRepository;
import com.example.moviebooking.service.BookingService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService service;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    public BookingController(BookingService service, 
                            UserRepository userRepository,
                            ShowRepository showRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @PostMapping
    public Booking addBooking(@RequestBody BookingRequest request) {
        // Get current authenticated user
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get show
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Create booking with user and show
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeatsBooked(request.getSeatsBooked());
        booking.setBookingDate(LocalDateTime.now());

        return service.addBooking(
                booking,
                request.getAmount(),
                request.getPaymentMethod(),
                request.getPaymentStatus()
        );
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/my-bookings")
    public List<Booking> getUserBookings() {
        // Get current authenticated user
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return service.getUserBookings(user.getId());
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return service.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        return service.deleteBooking(id);
    }

    @GetMapping("/page")
    public Page<Booking> getBookings(
            @RequestParam int page,
            @RequestParam int size) {

        return service.getBookings(page, size);
    }
}