package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.dto.LaundryFormDto;
import com.laundry.laundrybackend.model.Laundry;
import com.laundry.laundrybackend.repository.LaundryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class LaundryService {

    @Autowired
    private LaundryRepository laundryRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use injected bean instead of creating new instance

    private final SecureRandom random = new SecureRandom();

    public String registerLaundry(LaundryFormDto formDto) {
        try {
            // Check if email already exists
            if (laundryRepository.existsByEmail(formDto.getEmail())) {
                throw new RuntimeException("Email already registered");
            }

            // Generate temporary password (not the final password)
            String temporaryPassword = generateTemporaryPassword();

            // Create and save user
            Laundry user = new Laundry();
            user.setName(formDto.getName());
            user.setAddress(formDto.getAddress());
            user.setWorkHours(formDto.getWorkHours());
            user.setServices(formDto.getServices());
            user.setAvailability(formDto.getAvailability());
            user.setEmail(formDto.getEmail());
            user.setShopImage(formDto.getShopImage());
            user.setLicenseImage(formDto.getLicenseImage());

            // Set temporary password and activation status
            user.setTemporaryPassword(temporaryPassword);
            user.setIsActivated(false); // Account not activated yet
            // Don't set passwordHash yet - it will be set during activation

            laundryRepository.save(user);

            // Send email with temporary password for activation
            emailService.sendPasswordEmail(formDto.getEmail(), formDto.getName(), temporaryPassword);

            return "Registration successful! Activation instructions sent to email.";

        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    // Generate a temporary password for account activation
    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    // Alternative method signature to match the document example
    public Laundry saveLaundry(String name, String address, String workHours,
                               List<String> services, String availability, String email,
                               String shopImage, String licenseImage) {

        // Generate temporary password
        String tempPassword = generateTemporaryPassword();

        Laundry laundry = new Laundry();
        laundry.setName(name);
        laundry.setAddress(address);
        laundry.setWorkHours(workHours);
        laundry.setServices(services);
        laundry.setAvailability(availability);
        laundry.setEmail(email);
        laundry.setShopImage(shopImage);
        laundry.setLicenseImage(licenseImage);
        laundry.setTemporaryPassword(tempPassword); // Store temp password
        laundry.setIsActivated(false); // Set as not activated initially

        Laundry savedLaundry = laundryRepository.save(laundry);

        // Send email with tempPassword to the user
        try {
            emailService.sendPasswordEmail(email, name, tempPassword);
            System.out.println("Activation email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send activation email: " + e.getMessage());
        }

        return savedLaundry;
    }

    // Additional utility methods
    public Optional<Laundry> findByEmail(String email) {
        return laundryRepository.findByEmail(email);
    }

    public boolean isEmailRegistered(String email) {
        return laundryRepository.existsByEmail(email);
    }

    public boolean isAccountActivated(String email) {
        Optional<Laundry> laundryOpt = laundryRepository.findByEmail(email);
        return laundryOpt.isPresent() &&
                laundryOpt.get().getIsActivated() != null &&
                laundryOpt.get().getIsActivated();
    }
}