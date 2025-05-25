package com.laundry.laundrybackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "laundry")
public class Laundry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "work_hours", nullable = false)
    private String workHours;

    @ElementCollection
    @CollectionTable(name = "laundry_services", joinColumns = @JoinColumn(name = "laundry_id"))
    @Column(name = "service")
    private List<String> services;

    @Column(nullable = false)
    private String availability;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    // ADD THIS FIELD for temporary password
    @Column(name = "temporary_password")
    private String temporaryPassword;

    @Lob
    @Column(name = "shop_image", columnDefinition = "LONGTEXT")
    private String shopImage;

    @Lob
    @Column(name = "license_image", columnDefinition = "LONGTEXT")
    private String licenseImage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    // ADD THIS FIELD for activation status
    @Column(name = "is_activated")
    private Boolean isActivated = false;

    @Column(name = "rating")
    private Double rating;
    public Double getRating() {
        return rating;
    }
    public void setrating(Double rating) {
        this.rating = rating;
    }

    // Constructors
    public Laundry() {
        this.createdAt = LocalDateTime.now();
    }

    // All your existing getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getWorkHours() { return workHours; }
    public void setWorkHours(String workHours) { this.workHours = workHours; }

    public List<String> getServices() { return services; }
    public void setServices(List<String> services) { this.services = services; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getShopImage() { return shopImage; }
    public void setShopImage(String shopImage) { this.shopImage = shopImage; }

    public String getLicenseImage() { return licenseImage; }
    public void setLicenseImage(String licenseImage) { this.licenseImage = licenseImage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    // ADD THESE NEW GETTERS/SETTERS
    public String getTemporaryPassword() { return temporaryPassword; }
    public void setTemporaryPassword(String temporaryPassword) { this.temporaryPassword = temporaryPassword; }

    public Boolean getIsActivated() { return isActivated; }
    public void setIsActivated(Boolean isActivated) { this.isActivated = isActivated; }
}
