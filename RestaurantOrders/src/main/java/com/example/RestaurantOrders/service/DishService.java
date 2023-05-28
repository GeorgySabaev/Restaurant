package com.example.RestaurantOrders.service;

import com.example.RestaurantOrders.model.Dish;

public interface DishService {
    public long createDish(Dish dish, String token);
    public Dish readDish(long id, String token);
    public void updateDish(Dish dish, String token);
    public void deleteDish(long id, String token);
}
