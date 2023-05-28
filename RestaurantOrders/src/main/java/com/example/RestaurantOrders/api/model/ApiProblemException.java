package com.example.RestaurantOrders.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiProblemException extends RuntimeException {

    private final HttpStatus status;
    public ApiProblemException(
            HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
