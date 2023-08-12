package com.mri.orderservice.controller;

import com.mri.orderservice.dto.OrderRequest;
import com.mri.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return "Request has been processed successfully!";
    }
}
