package order.entity;

import order.valueObject.*;

public class Order {
    private final OrderId orderId;
    private final BranchID branchID;
    private final PhoneID phoneID;
    private final BranchName branchName;
    private final PhoneName phoneName;
    private final Quantity quantity;
    private final OrderDate orderDate;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.branchID = builder.branchID;
        this.phoneID = builder.phoneID;
        this.branchName = builder.branchName;
        this.phoneName = builder.phoneName;
        this.quantity = builder.quantity;
        this.orderDate = builder.orderDate;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public BranchID getBranchID() {
        return branchID;
    }

    public PhoneID getPhoneID() {
        return phoneID;
    }

    public BranchName getBranchName() {
        return branchName;
    }

    public PhoneName getPhoneName() {
        return phoneName;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public OrderDate getOrderDate() {
        return orderDate;
    }

    public static class Builder {
        private final OrderId orderId = new OrderId();          // Auto-generated
        private final OrderDate orderDate = new OrderDate();    // Auto-generated
        private BranchID branchID;
        private PhoneID phoneID;
        private BranchName branchName;
        private PhoneName phoneName;
        private Quantity quantity;

        public Builder branchID(BranchID branchID) {
            this.branchID = branchID;
            return this;
        }

        public Builder phoneID(PhoneID phoneID) {
            this.phoneID = phoneID;
            return this;
        }

        public Builder branchName(BranchName branchName) {
            this.branchName = branchName;
            return this;
        }

        public Builder phoneName(PhoneName phoneName) {
            this.phoneName = phoneName;
            return this;
        }

        public Builder quantity(Quantity quantity) {
            this.quantity = quantity;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId.getOrderId() +
                ", branchID=" + branchID.getBranchID() +
                ", phoneID=" + phoneID.getPhoneID() +
                ", branchName=" + branchName.getName() +
                ", phoneName=" + phoneName.getName() +
                ", quantity=" + quantity.getQuantity() +
                ", orderDate=" + orderDate.getOrderDate() +
                '}';
    }
}
