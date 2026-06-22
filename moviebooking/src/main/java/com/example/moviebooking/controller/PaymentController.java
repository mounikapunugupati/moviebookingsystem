package com.example.moviebooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.moviebooking.model.Payment;
import com.example.moviebooking.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment addPayment(@RequestBody Payment payment) {
        return service.addPayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment) {

        return service.updatePayment(id, payment);
    }

    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {
        return service.deletePayment(id);
    }
    @PutMapping("/{id}/success")
public Payment paymentSuccess(
        @PathVariable Long id) {

    return service.updatePaymentStatus(
            id,
            "SUCCESS");
}
}