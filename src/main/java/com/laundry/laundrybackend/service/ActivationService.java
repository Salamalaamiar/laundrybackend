package com.laundry.laundrybackend.service;

import org.springframework.stereotype.Service;

@Service
public class ActivationService {

    public boolean activateAccount(String email, String tempPassword, String newPassword) {
        // Exemple simple — à remplacer par votre logique réelle
        // Ex: Vérifier tempPassword dans une DB, puis enregistrer le nouveau

        if (tempPassword.equals("123456")) {
            System.out.println("Activation validée pour " + email);
            return true;
        }

        return false;
    }
}