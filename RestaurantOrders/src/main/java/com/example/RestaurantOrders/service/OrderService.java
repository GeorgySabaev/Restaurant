package com.example.RestaurantOrders.service;

import com.example.RestaurantOrders.api.model.PlaceOrderRequest;
import com.example.RestaurantOrders.api.model.PlaceOrderResponse;

public interface OrderService {
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request, String token);
    public String getStatus(long id);
}
