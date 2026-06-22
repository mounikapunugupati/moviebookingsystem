package com.example.moviebooking.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.moviebooking.dto.PaymentStatusMessage;
import com.example.moviebooking.model.Payment;
import com.example.moviebooking.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public PaymentService(
            PaymentRepository repository,
            SimpMessagingTemplate messagingTemplate) {

        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    public Payment addPayment(Payment payment) {

        payment.setPaymentStatus("PENDING");

        Payment savedPayment = repository.save(payment);

        PaymentStatusMessage message =
                new PaymentStatusMessage();

        message.setPaymentId(savedPayment.getId());
        message.setStatus("PENDING");

        messagingTemplate.convertAndSend(
                "/topic/payments",
                message);

        return savedPayment;
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Payment Not Found"));
    }

    public Payment updatePayment(
            Long id,
            Payment payment) {

        Payment existingPayment =
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Payment Not Found"));

        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setPaymentStatus(payment.getPaymentStatus());
        existingPayment.setBooking(payment.getBooking());

        return repository.save(existingPayment);
    }

    public Payment updatePaymentStatus(
            Long paymentId,
            String status) {

        Payment payment =
                repository.findById(paymentId)
                        .orElseThrow(
                                () -> new RuntimeException("Payment Not Found"));

        payment.setPaymentStatus(status);

        Payment updatedPayment =
                repository.save(payment);

        PaymentStatusMessage message =
                new PaymentStatusMessage();

        message.setPaymentId(paymentId);
        message.setStatus(status);

        messagingTemplate.convertAndSend(
                "/topic/payments",
                message);

        return updatedPayment;
    }

    public String deletePayment(Long id) {

        repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Payment Not Found"));

        repository.deleteById(id);

        return "Payment Deleted Successfully";
    }
}