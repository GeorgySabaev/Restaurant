package com.example.RestaurantOrders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(
        name = "order_dish"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dish dish;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(precision=10, scale=2, name = "price", nullable = false)
    private BigDecimal price;
}
