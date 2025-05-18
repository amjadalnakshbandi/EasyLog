package com.example.plugin.controller.order;

import com.example.plugin.model.order.OrderResponse;
import com.example.plugin.persistence.order.OrderRepositoryBridge;
import order.OrderDto;
import order.entity.Order;
import order.valueObject.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ApiResponse;
import response.ErrorResponse;
import response.SuccessResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepositoryBridge orderRepositoryBridge;

    @Autowired
    public OrderController(OrderRepositoryBridge orderRepositoryBridge) {
        this.orderRepositoryBridge = orderRepositoryBridge;
    }

    @PostMapping
    public ResponseEntity<? extends ApiResponse> addOrder(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody OrderDto dto) {

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new SecurityException("Fehlender oder ungültiger Authorization-Header");
            }

            Order order = new Order.Builder()
                    .phoneID(new PhoneID(dto.getPhoneId()))
                    .phoneName(new PhoneName(dto.getPhoneName()))
                    .branchID(new BranchID(dto.getBranchId()))
                    .branchName(new BranchName(dto.getBranchName()))
                    .quantity(new Quantity(dto.getQuantity()))
                    .build();

            orderRepositoryBridge.addOrder(order);

            OrderResponse response = new OrderResponse(
                    order.getOrderId().getOrderId(),
                    order.getPhoneID().getPhoneID(),
                    order.getPhoneName().getName(),
                    order.getBranchID().getBranchID(),
                    order.getBranchName().getName(),
                    order.getQuantity().getQuantity(),
                    order.getOrderDate().getOrderDate()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Bestellung erfolgreich erstellt", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage(), "Authentication Failed"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage(), "Internal Server Error"));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage(), "Security Error"));
        }
    }
}
