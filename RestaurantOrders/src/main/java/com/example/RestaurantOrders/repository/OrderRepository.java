package com.example.RestaurantOrders.repository;

import com.example.RestaurantOrders.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
