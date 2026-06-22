package com.example.moviebooking.dto;

import lombok.Data;

@Data
public class PaymentStatusMessage {

    private Long paymentId;

    private String status;
}