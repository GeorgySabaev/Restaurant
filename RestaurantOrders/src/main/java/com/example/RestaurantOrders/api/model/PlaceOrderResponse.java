package com.example.RestaurantOrders.api.model;

import lombok.Data;

@Data
public class PlaceOrderResponse {
    public String message;
    public long order_id;
}
