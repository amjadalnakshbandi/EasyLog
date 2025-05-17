package order.valueObject;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderId {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private final String orderId;

    public OrderId() {
        this.orderId = generateOrderId();
    }

    private String generateOrderId() {
        int number = counter.getAndIncrement();
        return String.format("O%03d", number);
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId1 = (OrderId) o;
        return Objects.equals(orderId, orderId1.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
