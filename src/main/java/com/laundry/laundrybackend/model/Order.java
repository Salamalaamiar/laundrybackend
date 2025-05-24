package com.laundry.laundrybackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime pickupDate;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private Boolean washing = false;

    @Column(nullable = false)
    private Boolean ironing = false;

    @Column(nullable = false)
    private Boolean drying = false;

    @Column(nullable = false)
    private Boolean delivery = false;

    @Column(nullable = false)
    private String status = "pending";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters et setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getWashing() {
        return washing;
    }

    public void setWashing(Boolean washing) {
        this.washing = washing;
    }

    public Boolean getIroning() {
        return ironing;
    }

    public void setIroning(Boolean ironing) {
        this.ironing = ironing;
    }

    public Boolean getDrying() {
        return drying;
    }

    public void setDrying(Boolean drying) {
        this.drying = drying;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Pas de setter pour createdAt car il est généré automatiquement

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Pas de setter pour updatedAt car il est généré automatiquement
}
