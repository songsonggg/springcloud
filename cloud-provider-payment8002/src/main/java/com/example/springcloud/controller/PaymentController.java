package com.example.springcloud.controller;

import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value="/payment/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("payment create result: " + result);

        if (result > 0){
            return new CommonResult(200, "create success, port: " + serverPort, result);
        }else{
            return new CommonResult(400, "create fail, port: " + serverPort, null);
        }
    }


    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable Long id){
        Payment payment = paymentService.getPaymentById(id);

        if (payment == null){
            return new CommonResult(400, "get payment fail, port: " + serverPort, null);
        }

        return new CommonResult(200, "get payment success, port: " + serverPort, payment);
    }
}
