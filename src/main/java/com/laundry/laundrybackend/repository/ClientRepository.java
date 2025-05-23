package com.laundry.laundrybackend.repository;

import com.laundry.laundrybackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Rechercher un client par email ET mot de passe
    Optional<Client> findByEmailAndPassword(String email, String password);


    Optional<Client> findByEmail(String email);
}

