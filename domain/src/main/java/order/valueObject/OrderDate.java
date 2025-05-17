package order.valueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class OrderDate {
    private final String orderDate;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderDate() {
        this.orderDate = LocalDateTime.now().format(FORMATTER);
    }

    public String getOrderDate() {
        return orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDate that = (OrderDate) o;
        return Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderDate);
    }

    @Override
    public String toString() {
        return orderDate;
    }
}
