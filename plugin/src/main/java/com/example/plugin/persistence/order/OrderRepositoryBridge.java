package com.example.plugin.persistence.order;

import order.OrderService;
import order.entity.Order;
import order.repository.OrderRepository;
import order.service.OrderLimitPolicyService;

import java.io.IOException;

public class OrderRepositoryBridge implements OrderRepository {

    private final OrderService orderService;

    public OrderRepositoryBridge() {
        this.orderService = new OrderService(new OrderLimitPolicyService());
    }

    @Override
    public void addOrder(Order order) throws IOException {
        orderService.addOrderImplementation(order);
    }
}
