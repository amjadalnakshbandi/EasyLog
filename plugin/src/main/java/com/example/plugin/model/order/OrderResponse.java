package com.example.plugin.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResponse {

    @JsonProperty("orderId")
    private final String orderId;

    @JsonProperty("phoneId")
    private final String phoneId;

    @JsonProperty("phoneName")
    private final String phoneName;

    @JsonProperty("branchId")
    private final String branchId;

    @JsonProperty("branchName")
    private final String branchName;

    @JsonProperty("quantity")
    private final int quantity;

    @JsonProperty("orderDate")
    private final String orderDate;

    public OrderResponse(String orderId, String phoneId, String phoneName, String branchId, String branchName, int quantity, String orderDate) {
        this.orderId = orderId;
        this.phoneId = phoneId;
        this.phoneName = phoneName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public String getOrderId() { return orderId; }
    public String getPhoneId() { return phoneId; }
    public String getPhoneName() { return phoneName; }
    public String getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }
    public int getQuantity() { return quantity; }
    public String getOrderDate() { return orderDate; }
}
