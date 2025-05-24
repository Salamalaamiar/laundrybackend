package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.dto.LaundryFormDto;
import com.laundry.laundrybackend.model.Laundry;
import com.laundry.laundrybackend.repository.LaundryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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

            // Generate random password
            String randomPassword = generateRandomPassword();

            // Create and save user
            Laundry user = new Laundry();
            user.setName(formDto.getName());
            user.setAddress(formDto.getAddress());
            user.setWorkHours(formDto.getWorkHours());
            user.setServices(formDto.getServices());
            user.setAvailability(formDto.getAvailability());
            user.setEmail(formDto.getEmail());
            user.setPasswordHash(passwordEncoder.encode(randomPassword));
            user.setShopImage(formDto.getShopImage());
            user.setLicenseImage(formDto.getLicenseImage());

            laundryRepository.save(user);

            // Send email with password
            emailService.sendPasswordEmail(formDto.getEmail(), formDto.getName(), randomPassword);

            return "Registration successful! Password sent to email.";

        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}