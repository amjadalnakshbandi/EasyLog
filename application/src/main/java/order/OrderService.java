package order;

import order.entity.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OrderService {

    private static final String csv_orders = "Data/orders.csv";

    public void addOderImplementation(Order order) {
        try {
            // Check if file exists and has content
            boolean fileExists = Files.exists(Paths.get(csv_orders)) && Files.size(Paths.get(csv_orders)) > 0;

            FileWriter writer = new FileWriter(csv_orders, true); // append mode

            if (!fileExists) {
                // Write header if file is new/empty
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

            System.out.println("Order added to CSV successfully: " + order.toString());
        } catch (IOException e) {
            System.err.println("Error saving order to CSV: " + e.getMessage());
        }
    }
}
