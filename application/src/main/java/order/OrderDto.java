package order;

import order.entity.Order;
import order.valueObject.*;

import java.io.IOException;

public class OrderDto {
    private String orderId;
    private String branchId;
    private String phoneId;
    private String branchName;
    private String phoneName;
    private int quantity;
    private String orderDate;

    public OrderDto() {
        // Default constructor for frameworks like Jackson
    }

    // Constructor to create a DTO from the domain object
    public OrderDto(Order order) {
        this.orderId = order.getOrderId().getOrderId();
        this.branchId = order.getBranchID().getBranchID();
        this.phoneId = order.getPhoneID().getPhoneID();
        this.branchName = order.getBranchName().getName();
        this.phoneName = order.getPhoneName().getName();
        this.quantity = order.getQuantity().getQuantity();
        this.orderDate = order.getOrderDate().getOrderDate();
    }

    // Converts this DTO to a domain Order object
    public Order toOrder() throws IOException {
        return new Order.Builder()
                .branchID(new BranchID(this.branchId))
                .phoneID(new PhoneID(this.phoneId))
                .branchName(new BranchName(this.branchName))
                .phoneName(new PhoneName(this.phoneName))
                .quantity(new Quantity(this.quantity))
                .build();
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBranchId() {
        return branchId;
    }
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getPhoneId() {
        return phoneId;
    }
    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhoneName() {
        return phoneName;
    }
    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
