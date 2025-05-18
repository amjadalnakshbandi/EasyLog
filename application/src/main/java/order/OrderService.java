package order;

import constants.constants;
import order.entity.Order;
import order.service.OrderLimitPolicyService;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OrderService {

    Path csv_orders_path = Paths.get(constants.CSV_Orders_PATH);

    private final OrderLimitPolicyService orderLimitPolicyService = new OrderLimitPolicyService();

    public void addOrderImplementation(Order order) throws IOException, IllegalArgumentException {
        // Step 1: Validate order policy
        try {
            orderLimitPolicyService.validateOrderLimit(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Order limit validation failed: " + e.getMessage(), e);
        }

        // Step 2: Write to CSV
        try {
            boolean fileExists = Files.exists(csv_orders_path) && Files.size(csv_orders_path) > 0;

            try (FileWriter writer = new FileWriter(String.valueOf(csv_orders_path), true)) {
                if (!fileExists) {
                    writer.append("orderId,phoneId,phoneName,branchId,branchName,quantity,orderDate\n");
                }

                writer.append(order.getOrderId().getOrderId()).append(",");
                writer.append(order.getPhoneID().getPhoneID()).append(",");
                writer.append(order.getPhoneName().getName()).append(",");
                writer.append(order.getBranchID().getBranchID()).append(",");
                writer.append(order.getBranchName().getName()).append(",");
                writer.append(String.valueOf(order.getQuantity().getQuantity())).append(",");
                writer.append(order.getOrderDate().getOrderDate()).append("\n");
            }

        } catch (IOException e) {
            throw new IOException("Error writing order to CSV file", e);
        }
    }
}
