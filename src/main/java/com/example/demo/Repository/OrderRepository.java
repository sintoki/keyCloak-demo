package com.example.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByRestaurantId(Long restaurantId);

}
