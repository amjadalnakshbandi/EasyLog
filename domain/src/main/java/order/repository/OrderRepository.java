package order.repository;

import order.entity.Order;

import java.io.IOException;

public interface OrderRepository {
    void addOrder(Order order) throws IOException;
}
