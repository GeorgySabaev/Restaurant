package com.example.RestaurantOrders.service;

import com.example.RestaurantOrders.api.model.ApiProblemException;
import com.example.RestaurantOrders.api.model.PlaceOrderRequest;
import com.example.RestaurantOrders.api.model.PlaceOrderResponse;
import com.example.RestaurantOrders.model.Dish;
import com.example.RestaurantOrders.model.Order;
import com.example.RestaurantOrders.model.OrderDish;
import com.example.RestaurantOrders.model.User;
import com.example.RestaurantOrders.repository.DishRepository;
import com.example.RestaurantOrders.repository.OrderDishRepository;
import com.example.RestaurantOrders.repository.OrderRepository;
import com.example.RestaurantOrders.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.data.DataRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public DishRepository dishRepository;
    @Autowired
    public OrderDishRepository orderDishRepository;

    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request, String token) {
        User user = JwtUtils.parseToken(token);
        String role = user.role;
        long user_id = user.id;

        if(!Objects.equals(role, "customer")){
            throw new ApiProblemException(
                    HttpStatus.FORBIDDEN,
                    "Incorrect role");
        }

        if (request == null) {
            throw new ApiProblemException(
                    HttpStatus.BAD_REQUEST,
                    "No request");
        }

        if (request.getDishes() == null || request.getDishes().isEmpty()) {
            throw new ApiProblemException(
                    HttpStatus.BAD_REQUEST,
                    "No dishes");
        }
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        for (var dish : request.getDishes()) {
            if (dish == null || dish.getName() == null) {
                throw new ApiProblemException(
                        HttpStatus.BAD_REQUEST,
                        "Dish not specified");
            }
            Optional<Dish> dishQuery = dishRepository.findByName(dish.getName());
            if (dishQuery.isEmpty()) {
                throw new ApiProblemException(
                        HttpStatus.BAD_REQUEST,
                        String.format("Dish \"%s\" does not exist", dish.getName()));
            }
            if (dishQuery.get().getQuantity() < dish.getQuantity()) {
                throw new ApiProblemException(
                        HttpStatus.BAD_REQUEST,
                        String.format("Not enough \"%s\" in stock", dish.getName()));
            }
            dishes.add(dishQuery.get());

        }

        Order order = new Order();
        order.setSpecial_requests(request.getSpecial_requests());
        order.setStatus("Waiting");
        order.setUser_id(user_id);
        orderRepository.save(order);

        for (int i = 0; i < dishes.size(); ++i) {
            OrderDish orderDish = new OrderDish();
            orderDish.setOrder(order);
            orderDish.setDish(dishes.get(i));
            orderDish.setPrice(dishes.get(i).getPrice());
            orderDish.setQuantity(request.getDishes().get(i).getQuantity());
            orderDishRepository.save(orderDish);
        }
        PlaceOrderResponse response = new PlaceOrderResponse();
        response.order_id = order.getId();
        return response;
    }

    @Transactional
    public String getStatus(long id) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new ApiProblemException(
                    HttpStatus.NOT_FOUND,
                    "No dish with such id");
        }
        return order.get().getStatus();
    }
}
