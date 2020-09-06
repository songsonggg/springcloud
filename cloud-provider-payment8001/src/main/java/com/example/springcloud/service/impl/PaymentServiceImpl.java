package com.example.springcloud.service.impl;

import com.example.springcloud.dao.PaymentDao;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentDao paymentDao;


    @Override
    public Payment getPaymentById(Long id){
        return paymentDao.getPaymentById(id);
    }

    @Override
    public int create(Payment payment){
        return paymentDao.create(payment);
    }

    @Override
    public int createPayments(List<Payment> payments){
        return paymentDao.createPayments(payments);
    }
}
