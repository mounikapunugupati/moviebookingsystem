package com.example.moviebooking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.moviebooking.model.Booking;
import com.example.moviebooking.model.Payment;
import com.example.moviebooking.repository.BookingRepository;
import com.example.moviebooking.repository.PaymentRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public BookingService(BookingRepository bookingRepository,
                          PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    public Booking addBooking(Booking booking,
                              Double amount,
                              String paymentMethod,
                              String paymentStatus) {

        Booking savedBooking = bookingRepository.save(booking);

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(paymentStatus);
        payment.setBooking(savedBooking);

        paymentRepository.save(payment);

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));
    }

    public String deleteBooking(Long id) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));

        bookingRepository.deleteById(id);
        return "Booking Deleted Successfully";
    }

    public Page<Booking> getBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findAll(pageable);
    }
}