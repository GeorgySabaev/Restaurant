package com.example.RestaurantOrders.repository;

import com.example.RestaurantOrders.model.OrderDish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderDishRepository extends CrudRepository<OrderDish, Long> {
}
