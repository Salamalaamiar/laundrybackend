package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.dto.OrderDTO;
import com.laundry.laundrybackend.model.Order;
import com.laundry.laundrybackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // EXISTING METHODS - Your original implementation
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // NEW METHODS - Enhanced functionality with DTO
    public List<OrderDTO> getPendingOrdersByClient(Long clientId) {
        List<Order> orders = orderRepository.findByClientIdAndStatus(clientId, "pending");
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getActiveOrdersByClient(Long clientId) {
        List<String> activeStatuses = List.of("processing", "washing", "drying", "ironing", "ready");
        List<Order> orders = orderRepository.findByClientIdAndStatusIn(clientId, activeStatuses);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersByClient(Long clientId) {
        List<Order> orders = orderRepository.findByClientIdOrderByCreatedAtDesc(clientId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderByIdAndClient(Long orderId, Long clientId) {
        Order order = orderRepository.findByOrderIdAndClientId(orderId, clientId);
        return order != null ? convertToDTO(order) : null;
    }

    private OrderDTO convertToDTO(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm");

        // Format dates
        String pickupDate = order.getPickupDate() != null
                ? order.getPickupDate().format(formatter)
                : null;

        String estimatedDelivery = order.getDeliveryDate() != null
                ? order.getDeliveryDate().format(formatter)
                : null;

        // Build services string
        List<String> servicesList = new ArrayList<>();
        if (order.getWashing() != null && order.getWashing()) servicesList.add("Washing");
        if (order.getDrying() != null && order.getDrying()) servicesList.add("Drying");
        if (order.getIroning() != null && order.getIroning()) servicesList.add("Ironing");
        if (order.getDelivery() != null && order.getDelivery()) servicesList.add("Delivery");
        String services = String.join(", ", servicesList);

        // Determine order type based on services
        String type = determineOrderType(order);

        // Calculate estimated total
        String total = calculateTotal(order);

        return new OrderDTO(
                order.getOrderId().toString(),
                "ORD-" + order.getOrderId(),
                total,
                type,
                order.getStatus() != null ? order.getStatus() : "pending",
                estimatedDelivery,
                order.getAddress(),
                pickupDate,
                services,
                order.getCreatedAt()
        );
    }

    private String determineOrderType(Order order) {
        boolean washing = order.getWashing() != null && order.getWashing();
        boolean ironing = order.getIroning() != null && order.getIroning();
        boolean drying = order.getDrying() != null && order.getDrying();

        if (washing && ironing) {
            return "laundry"; // Full service
        } else if (washing) {
            return "washing";
        } else if (ironing) {
            return "ironing";
        } else if (drying) {
            return "drying";
        }
        return "general";
    }

    private String calculateTotal(Order order) {
        int basePrice = 0;
        if (order.getWashing() != null && order.getWashing()) basePrice += 50;
        if (order.getDrying() != null && order.getDrying()) basePrice += 30;
        if (order.getIroning() != null && order.getIroning()) basePrice += 40;
        if (order.getDelivery() != null && order.getDelivery()) basePrice += 20;

        return String.valueOf(Math.max(basePrice, 100)); // Minimum 100 DH
    }
}