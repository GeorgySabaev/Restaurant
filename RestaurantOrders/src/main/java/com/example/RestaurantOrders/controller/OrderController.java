package com.example.RestaurantOrders.controller;

import com.example.RestaurantOrders.api.model.*;
import com.example.RestaurantOrders.repository.OrderRepository;
import com.example.RestaurantOrders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity place(@RequestHeader("Token") String token, @RequestBody PlaceOrderRequest request) {
        try {
            var response = orderService.placeOrder(request, token);
            response.setMessage("Order placed");
            return ResponseEntity.ok(response);
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping(path = "/status")
    public ResponseEntity login(@RequestBody long orderId) {
        try {
            return ResponseEntity.ok(orderService.getStatus(orderId));
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}
