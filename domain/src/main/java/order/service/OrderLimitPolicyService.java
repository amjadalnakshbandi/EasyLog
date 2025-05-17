package order.service;

import order.entity.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderLimitPolicyService {

    private static final String CSV_FILE = "Data/orders.csv";
    private static final int DAILY_LIMIT = 100;

    public void validateOrderLimit(Order order) {
        int totalQuantityToday = 0;
        LocalDate today = LocalDate.parse(order.getOrderDate().getOrderDate().substring(0, 10), DateTimeFormatter.ISO_DATE);
        String branchId = order.getBranchID().getBranchID();
        int requestedQuantity = order.getQuantity().getQuantity();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 7) continue;

                String csvBranchId = parts[3].trim();
                String orderDateStr = parts[6].trim();
                int quantity = Integer.parseInt(parts[5].trim());

                LocalDate orderDate = LocalDate.parse(orderDateStr.substring(0, 10), DateTimeFormatter.ISO_DATE);

                if (csvBranchId.equals(branchId) && orderDate.equals(today)) {
                    totalQuantityToday += quantity;
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new IllegalStateException("Fehler beim Lesen der Bestellungen: " + e.getMessage());
        }

        if ((totalQuantityToday + requestedQuantity) > DAILY_LIMIT) {
            throw new IllegalStateException(
                    "Bestellgrenze überschritten: Maximal erlaubt sind " + DAILY_LIMIT +
                            " Einheiten pro Tag für Filiale " + branchId + ". Heute bereits bestellt: " +
                            totalQuantityToday + ", angefragt: " + requestedQuantity
            );
        }
    }
}
