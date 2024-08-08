package com.example.demo.Controller;

import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

//     what i need to do here think and

    @GetMapping
    @RequestMapping("/{restaurantId}/list")
    @PreAuthorize("hasRole('manager')")
    // manager can access
    public List<Order> getOrders(@PathVariable Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @PostMapping
    // authenticated users can access
    public Order createOrder(@RequestBody Order order){
  orderRepository.save(order);

  order.getOrderItems().forEach(orderItem -> {
      orderItem.setOrderId(order.getId());
      orderItemRepository.save(orderItem);

          }
  );
  return  order;


    }


    @GetMapping
    @RequestMapping("/{orderId}")
    // manager can access
    public Order getOrderDetails(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        return order;
    }

}
