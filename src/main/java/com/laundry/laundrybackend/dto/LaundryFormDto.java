// 1. Updated LaundryFormDto.java
package com.laundry.laundrybackend.dto;

import java.util.List;

public class LaundryFormDto {
    private String name;
    private String address;
    private String workHours;
    private List<String> services;
    private String availability;
    private String email;
    private String shopImage; // base64 string
    private String licenseImage; // base64 string

    // Constructors
    public LaundryFormDto() {}

    public LaundryFormDto(String name, String address, String workHours, List<String> services,
                          String availability, String email, String shopImage, String licenseImage) {
        this.name = name;
        this.address = address;
        this.workHours = workHours;
        this.services = services;
        this.availability = availability;
        this.email = email;
        this.shopImage = shopImage;
        this.licenseImage = licenseImage;
    }

    // Getters and setters
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

    public String getShopImage() { return shopImage; }
    public void setShopImage(String shopImage) { this.shopImage = shopImage; }

    public String getLicenseImage() { return licenseImage; }
    public void setLicenseImage(String licenseImage) { this.licenseImage = licenseImage; }
}
