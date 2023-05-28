package com.example.RestaurantOrders.repository;

import com.example.RestaurantOrders.model.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Long> {
    Optional<Dish> findByName (String name);
}