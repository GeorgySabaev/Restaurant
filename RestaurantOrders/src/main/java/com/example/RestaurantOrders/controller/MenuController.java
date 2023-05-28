package com.example.RestaurantOrders.controller;

import com.example.RestaurantOrders.api.model.ApiProblemException;
import com.example.RestaurantOrders.model.Dish;
import com.example.RestaurantOrders.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    public DishRepository dishRepository;
    @GetMapping
    public ResponseEntity place() {
        try {
            return ResponseEntity.ok(dishRepository.findAll());
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
