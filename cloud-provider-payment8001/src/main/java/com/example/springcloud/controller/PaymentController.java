package com.example.springcloud.controller;

import com.example.springcloud.entities.CommonResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    public static final String PAYMENT_SERVICE = "CLOUD-PAYMENT-SERVICE";
    @Autowired
    PaymentService paymentService;

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value="/payment/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("payment create result: " + result);

        if (result > 0){
            return new CommonResult(200, "create success, port" + serverPort, result);
        }else{
            return new CommonResult(400, "create fail, port" + serverPort, null);
        }
    }


    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable Long id){
        Payment payment = paymentService.getPaymentById(id);

        if (payment == null){
            return new CommonResult(400, "get payment fail, port" + serverPort, null);
        }

        return new CommonResult(200, "get payment success, port" + serverPort, payment);
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        log.info("order: " + discoveryClient.getOrder());
        List<String> services = discoveryClient.getServices();
        for (int i = 0; i < services.size(); i++) {
            String service = services.get(i);
            log.info("service: "  + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances(PAYMENT_SERVICE);
        for (int i = 0; i < instances.size(); i++) {
            ServiceInstance serviceInstance = instances.get(i);
            String instanceId = serviceInstance.getInstanceId();
            String host = serviceInstance.getHost();
            int port = serviceInstance.getPort();
            URI uri = serviceInstance.getUri();
            String serviceId = serviceInstance.getServiceId();
            log.info("instanceId: " + instanceId + " host: " + host + " port: " + port
            + " uri: " + uri + " serviceId: " + serviceId);
        }
        return discoveryClient;
    }
}
