package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.model.Laundry;
import com.laundry.laundrybackend.model.Fournisseur;
import com.laundry.laundrybackend.repository.LaundryRepository;
import com.laundry.laundrybackend.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ActivationService {

    @Autowired
    private LaundryRepository laundryRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public boolean activateAccount(String email, String tempPassword, String newPassword) {
        try {
            // Find laundry by email
            Optional<Laundry> laundryOpt = laundryRepository.findByEmail(email);
            if (!laundryOpt.isPresent()) {
                System.out.println("No laundry found with email: " + email);
                return false;
            }

            Laundry laundry = laundryOpt.get();

            // Check if already activated
            if (laundry.getIsActivated() != null && laundry.getIsActivated()) {
                System.out.println("Account already activated for: " + email);
                return false;
            }

            // Verify temporary password
            if (laundry.getTemporaryPassword() == null ||
                    !laundry.getTemporaryPassword().equals(tempPassword)) {
                System.out.println("Invalid temporary password for: " + email);
                return false;
            }

            // Hash the new password
            String hashedPassword = passwordEncoder.encode(newPassword);

            // Update laundry account
            laundry.setPasswordHash(hashedPassword);
            laundry.setIsActivated(true);
            laundry.setTemporaryPassword(null); // Clear temp password after activation
            laundryRepository.save(laundry);

            // Create fournisseur record
            Fournisseur fournisseur = new Fournisseur(
                    email,
                    hashedPassword,
                    laundry.getName(),
                    laundry
            );
            fournisseurRepository.save(fournisseur);

            System.out.println("Account activated successfully for: " + email);
            return true;

        } catch (Exception e) {
            System.err.println("Error activating account: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAccountActivated(String email) {
        Optional<Laundry> laundryOpt = laundryRepository.findByEmail(email);
        return laundryOpt.isPresent() &&
                laundryOpt.get().getIsActivated() != null &&
                laundryOpt.get().getIsActivated();
    }
}