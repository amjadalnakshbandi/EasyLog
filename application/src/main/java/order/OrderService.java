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

    public void addOrderImplementation(Order order) {
        // Step 1: Validate order policy (e.g., daily limit)
        orderLimitPolicyService.validateOrderLimit(order); // may throw IllegalStateException

        // Step 2: Write to CSV if valid
        try {
            Path csvOrdersPath =csv_orders_path;
            boolean fileExists = Files.exists(csvOrdersPath) && Files.size(csvOrdersPath) > 0;

            FileWriter writer = new FileWriter(String.valueOf(csv_orders_path), true); // append mode

            if (!fileExists) {
                // Write CSV header
                writer.append("orderId,phoneId,phoneName,branchId,branchName,quantity,orderDate\n");
            }

            // Write order data
            writer.append(order.getOrderId().getOrderId()).append(",");
            writer.append(order.getPhoneID().getPhoneID()).append(",");
            writer.append(order.getPhoneName().getName()).append(",");
            writer.append(order.getBranchID().getBranchID()).append(",");
            writer.append(order.getBranchName().getName()).append(",");
            writer.append(String.valueOf(order.getQuantity().getQuantity())).append(",");
            writer.append(order.getOrderDate().getOrderDate()).append("\n");

            writer.close();
            System.out.println("Order added to CSV successfully: " + order);

        } catch (IOException e) {
            throw new IllegalStateException("Fehler beim Schreiben der Bestellung in die CSV-Datei: " + e.getMessage());
        }
    }
}
