package com.example.RestaurantOrders.service;

import com.example.RestaurantOrders.api.model.ApiProblemException;
import com.example.RestaurantOrders.model.Dish;
import com.example.RestaurantOrders.model.User;
import com.example.RestaurantOrders.repository.DishRepository;
import com.example.RestaurantOrders.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    @Autowired
    public DishRepository dishRepository;

    @Transactional
    public long createDish(Dish dish, String token) {
        checkToken(token);
        try {
            if(dishRepository.findById(dish.getId()).isPresent()){
                throw new ApiProblemException(
                        HttpStatus.CONFLICT,
                        "Dish already exists");
            }
            dishRepository.save(dish);
        } catch (DataAccessException e) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "Cannot add dish");

        }
        return dish.getId();
    }

    @Transactional
    public Dish readDish(long id, String token) {
        checkToken(token);
        var dish = dishRepository.findById(id);
        if(dish.isEmpty()){
            throw new ApiProblemException(
                    HttpStatus.NOT_FOUND,
                    "No dish with such id");
        }
        return dish.get();
    }

    public void updateDish(Dish dish, String token) {
        checkToken(token);
        try {
            if(dishRepository.findById(dish.getId()).isEmpty()){
                throw new ApiProblemException(
                        HttpStatus.NOT_FOUND,
                        "No dish with such id");
            }
            dishRepository.save(dish);
        } catch (DataAccessException e) {
            throw new ApiProblemException(
                    HttpStatus.CONFLICT,
                    "Cannot add dish");

        }
    }

    @Transactional
    public void deleteDish(long id, String token) {
        checkToken(token);
        var dish = dishRepository.findById(id);
        if(dish.isEmpty()){
            throw new ApiProblemException(
                    HttpStatus.NOT_FOUND,
                    "No dish with such id");
        }
        dishRepository.deleteById(id);
    }

    private void checkToken(String token){
        User user = JwtUtils.parseToken(token);
        String role = user.role;

        if(!Objects.equals(role, "manager")){
            throw new ApiProblemException(
                    HttpStatus.FORBIDDEN,
                    "Incorrect role");
        }
    }
}
