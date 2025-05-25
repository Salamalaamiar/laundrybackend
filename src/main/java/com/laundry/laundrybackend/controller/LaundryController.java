package com.laundry.laundrybackend.controller;

import com.laundry.laundrybackend.model.Laundry;
import com.laundry.laundrybackend.repository.LaundryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/laundries")
@CrossOrigin(origins = "*") // autorise toutes les origines (tu peux restreindre après)
public class LaundryController {

    @Autowired
    private LaundryRepository laundryRepository;

    @GetMapping("/activated")
    public List<Laundry> getActivatedLaundries() {
        return laundryRepository.findByIsActivatedTrue(); // récupère uniquement les pressings activés
    }
}
