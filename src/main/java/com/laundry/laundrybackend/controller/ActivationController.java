package com.laundry.laundrybackend.controller;

import com.laundry.laundrybackend.dto.ActivationRequest;
import com.laundry.laundrybackend.service.ActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/laundry")
@CrossOrigin(origins = "*")  // autorise les requêtes React Native
public class ActivationController {

    private final ActivationService activationService;

    public ActivationController(ActivationService activationService) {
        this.activationService = activationService;
    }

    @PostMapping("/activate")
    public ResponseEntity<Map<String, Object>> activateAccount(@RequestBody ActivationRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request.getEmail() == null || request.getTemporaryPassword() == null || request.getNewPassword() == null) {
            response.put("success", false);
            response.put("message", "All fields are required.");
            return ResponseEntity.badRequest().body(response);
        }

        boolean success = activationService.activateAccount(
                request.getEmail(),
                request.getTemporaryPassword(),
                request.getNewPassword()
        );

        if (success) {
            response.put("success", true);
            response.put("message", "Account activated successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid temporary password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
