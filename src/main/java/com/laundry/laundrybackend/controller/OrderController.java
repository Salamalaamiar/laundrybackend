package com.laundry.laundrybackend.controller;

import com.laundry.laundrybackend.dto.OrderDTO;
import com.laundry.laundrybackend.model.Order;
import com.laundry.laundrybackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // Configure properly for production
public class OrderController {

    @Autowired
    private OrderService orderService;

    // NEW ENDPOINTS - From previous implementation
    @GetMapping("/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders(@RequestParam Long clientId) {
        try {
            List<OrderDTO> pendingOrders = orderService.getPendingOrdersByClient(clientId);
            return ResponseEntity.ok(pendingOrders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrderDTO>> getActiveOrders(@RequestParam Long clientId) {
        try {
            List<OrderDTO> activeOrders = orderService.getActiveOrdersByClient(clientId);
            return ResponseEntity.ok(activeOrders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByClient(@RequestParam Long clientId) {
        try {
            List<OrderDTO> allOrders = orderService.getAllOrdersByClient(clientId);
            return ResponseEntity.ok(allOrders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{orderId}/client/{clientId}")
    public ResponseEntity<OrderDTO> getOrderByIdAndClient(@PathVariable Long orderId, @PathVariable Long clientId) {
        try {
            OrderDTO order = orderService.getOrderByIdAndClient(orderId, clientId);

            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // EXISTING ENDPOINTS - Your original implementation
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.saveOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            return orderService.getOrderById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        try {
            return orderService.getOrderById(id)
                    .map(order -> {
                        order.setStatus(orderDetails.getStatus());
                        // Add more field updates as needed
                        if (orderDetails.getAddress() != null) {
                            order.setAddress(orderDetails.getAddress());
                        }
                        if (orderDetails.getPickupDate() != null) {
                            order.setPickupDate(orderDetails.getPickupDate());
                        }
                        if (orderDetails.getDeliveryDate() != null) {
                            order.setDeliveryDate(orderDetails.getDeliveryDate());
                        }
                        Order updated = orderService.saveOrder(order);
                        return ResponseEntity.ok(updated);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            return orderService.getOrderById(id)
                    .map(order -> {
                        orderService.deleteOrder(id);
                        return ResponseEntity.noContent().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}