package com.laundry.laundrybackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class OrderDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("total")
    private String total;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    @JsonProperty("estimatedDelivery")
    private String estimatedDelivery;

    @JsonProperty("address")
    private String address;

    @JsonProperty("pickupDate")
    private String pickupDate;

    @JsonProperty("services")
    private String services;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(String id, String orderNumber, String total, String type,
                    String status, String estimatedDelivery, String address,
                    String pickupDate, String services, LocalDateTime createdAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.total = total;
        this.type = type;
        this.status = status;
        this.estimatedDelivery = estimatedDelivery;
        this.address = address;
        this.pickupDate = pickupDate;
        this.services = services;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPickupDate() { return pickupDate; }
    public void setPickupDate(String pickupDate) { this.pickupDate = pickupDate; }

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}