package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.model.Fournisseur;
import com.laundry.laundrybackend.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**


    /**
     * Authentification d’un fournisseur.
     */
    public Optional<Fournisseur> authenticate(String email, String rawPassword) {
        Optional<Fournisseur> optionalFournisseur = fournisseurRepository.findByEmail(email);

        return optionalFournisseur.filter(fournisseur ->
                passwordEncoder.matches(rawPassword, fournisseur.getPassword()));
    }
}
