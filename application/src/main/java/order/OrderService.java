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

    private final Path csvOrdersPath = Paths.get(constants.CSV_Orders_PATH);
    private final OrderLimitPolicyService orderLimitPolicyService;

    // Dependency Injection via Constructor
    public OrderService(OrderLimitPolicyService orderLimitPolicyService) {
        this.orderLimitPolicyService = orderLimitPolicyService;
    }

    public void addOrderImplementation(Order order) throws IOException {
        validateOrder(order);
        writeOrderToCsv(order);
    }

    private void validateOrder(Order order) {
        try {
            orderLimitPolicyService.validateOrderLimit(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Order limit validation failed: " + e.getMessage(), e);
        }
    }

    private void writeOrderToCsv(Order order) throws IOException {
        boolean fileExists = Files.exists(csvOrdersPath) && Files.size(csvOrdersPath) > 0;

        try (FileWriter writer = new FileWriter(String.valueOf(csvOrdersPath), true)) {
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
        } catch (IOException e) {
            throw new IOException("Error writing order to CSV file", e);
        }
    }
}
