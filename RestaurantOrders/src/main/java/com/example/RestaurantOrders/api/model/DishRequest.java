package com.example.RestaurantOrders.api.model;

import lombok.Data;

@Data
public class DishRequest {
    String name;
    int quantity;
}
