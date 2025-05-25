package com.laundry.laundrybackend.controller;

import com.laundry.laundrybackend.model.Fournisseur;
import com.laundry.laundrybackend.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Contrôleur REST qui gère l'authentification des fournisseurs.
 */
@RestController
@RequestMapping("/api/fournisseur/auth") // Chemin de base spécifique aux fournisseurs
@CrossOrigin(origins = "*")
public class AuthFournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    /**
     * Inscription d'un fournisseur.
     */


    /**
     * Connexion d’un fournisseur.
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        return fournisseurService.authenticate(email, password)
                .map(fournisseur -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Login successful");
                    response.put("fournisseurId", fournisseur.getId());
                    response.put("email", fournisseur.getEmail());
                    return response;
                })
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Invalid credentials");
                    return response;
                });
    }
}
