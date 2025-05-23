package com.laundry.laundrybackend.controller;

// Importations nécessaires
import com.laundry.laundrybackend.service.ClientService;
import com.laundry.laundrybackend.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Contrôleur REST qui gère l'authentification des clients.
 */
@RestController
@RequestMapping("/api/auth") // Chemin de base pour toutes les routes de ce contrôleur
@CrossOrigin(origins = "*")  // Permet l'accès depuis n'importe quelle origine (utile pour React Native)
public class AuthController {

    // Injection du service qui contient la logique métier liée aux clients
    @Autowired
    private ClientService clientService;

    /**
     * Route POST pour la connexion d'un client.
     * Reçoit un JSON contenant "email" et "password".
     * Exemple de requête :
     * POST /api/auth/login
     * {
     *   "email": "client@example.com",
     *   "password": "123456"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Client client) {
        String result = clientService.registerClient(client);

        if (result.equals("Email already in use")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", result));
        }

        return ResponseEntity.ok(Map.of("success", true, "message", result));
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        // Récupère les données envoyées dans le corps de la requête
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Utilise le service pour authentifier le client
        return clientService.authenticate(email, password)
                .map(client -> {
                    // Si l'utilisateur est trouvé et le mot de passe est correct
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Login successful");
                    response.put("clientId", client.getId());
                    response.put("email", client.getEmail());
                    return response;
                })
                .orElseGet(() -> {
                    // Si aucun utilisateur correspondant
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("message", "Invalid credentials");
                    return response;
                });
    }
}
