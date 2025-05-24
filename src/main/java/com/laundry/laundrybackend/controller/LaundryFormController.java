package com.laundry.laundrybackend.controller;

import com.laundry.laundrybackend.dto.LaundryFormDto;
import com.laundry.laundrybackend.service.LaundryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/laundry")
@CrossOrigin
public class LaundryFormController {

    @Autowired
    private LaundryService laundryService;

    @PostMapping("/send")
    public ResponseEntity<?> registerLaundry(@RequestBody LaundryFormDto form) {
        try {
            // Validate required fields
            if (form.getName() == null || form.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Name is required"));
            }
            if (form.getEmail() == null || form.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Email is required"));
            }
            if (form.getAddress() == null || form.getAddress().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Address is required"));
            }
            if (form.getWorkHours() == null || form.getWorkHours().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Work hours are required"));
            }
            if (form.getServices() == null || form.getServices().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("At least one service is required"));
            }
            if (form.getAvailability() == null || form.getAvailability().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Availability is required"));
            }

            String result = laundryService.registerLaundry(form);
            return ResponseEntity.ok(createSuccessResponse(result));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(createErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
}