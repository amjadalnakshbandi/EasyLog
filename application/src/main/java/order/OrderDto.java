package order;

public class OrderDto {
    private String orderId;
    private String branchId;   // changed from branchID
    private String phoneId;    // changed from phoneID
    private String branchName;
    private String phoneName;
    private int quantity;
    private String orderDate;

    public OrderDto() {
        // default constructor
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getPhoneId() { return phoneId; }
    public void setPhoneId(String phoneId) { this.phoneId = phoneId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getPhoneName() { return phoneName; }
    public void setPhoneName(String phoneName) { this.phoneName = phoneName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
