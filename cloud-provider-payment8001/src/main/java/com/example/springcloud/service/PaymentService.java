package com.example.springcloud.service;



import com.example.springcloud.entities.Payment;

import java.util.List;

public interface PaymentService {
    Payment getPaymentById(Long id);

    int create(Payment payment);

    int createPayments(List<Payment> payments);
}
