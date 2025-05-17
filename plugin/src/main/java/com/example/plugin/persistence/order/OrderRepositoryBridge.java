package com.example.plugin.persistence.order;

import order.OrderService;
import order.entity.Order;
import order.repository.OrderRepository;

import java.io.IOException;

public class OrderRepositoryBridge implements OrderRepository {
    @Override
    public void addOrder(Order order) throws IOException {
        OrderService orderService = new OrderService();
        orderService.addOderImplementation(order);
    }
}
