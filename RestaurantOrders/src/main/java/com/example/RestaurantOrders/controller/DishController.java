package com.example.RestaurantOrders.controller;

import com.example.RestaurantOrders.api.model.ApiProblemException;
import com.example.RestaurantOrders.api.model.PlaceOrderRequest;
import com.example.RestaurantOrders.model.Dish;
import com.example.RestaurantOrders.repository.DishRepository;
import com.example.RestaurantOrders.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    public DishRepository dishRepository;
    @Autowired
    public DishService dishService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestHeader("Token") String token, @RequestBody Dish dish) {
        try {
            dishService.createDish(dish, token);
            return ResponseEntity.ok("dish placed");
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @GetMapping("/read")
    public ResponseEntity read(@RequestHeader("Token") String token, @RequestBody long id) {
        try {
            return ResponseEntity.ok(dishService.readDish(id, token));
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @PostMapping("/update")
    public ResponseEntity update(@RequestHeader("Token") String token, @RequestBody Dish dish) {
        try {
            dishService.updateDish(dish, token);
            return ResponseEntity.ok("Dish updated");
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestHeader("Token") String token, @RequestBody long id) {
        try {
            dishService.deleteDish(id, token);
            return ResponseEntity.ok("Dish deleted");
        } catch (ApiProblemException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
