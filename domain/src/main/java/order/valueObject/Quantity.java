package order.valueObject;

import java.io.IOException;
import java.util.Objects;

public class Quantity {

    private final int quantity;

    public Quantity(int quantity) throws IOException {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) throws IOException {
        if (quantity <= 0) {
            throw new IOException("Quantity must be greater than 0");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quantity that = (Quantity) o;
        return quantity == that.quantity;
    }

    @Override
    public String toString() {
        return ""+ quantity ;

    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

}
