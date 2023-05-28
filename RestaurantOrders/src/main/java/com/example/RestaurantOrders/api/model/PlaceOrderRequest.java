package com.example.RestaurantOrders.api.model;

import com.example.RestaurantOrders.model.Dish;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private List<DishRequest> dishes;
    private String special_requests;
}
