package com.laundry.laundrybackend.service;


import com.laundry.laundrybackend.model.Client;
import com.laundry.laundrybackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Authentification : email ET mot de passe
    public Optional<Client> authenticate(String email, String password) {
        return clientRepository.findByEmailAndPassword(email, password);
    }

    public String registerClient(Client client) {
        Optional<Client> existing = clientRepository.findByEmail(client.getEmail());

        if (existing.isPresent()) {
            return "Email already in use";
        }

        // Tu peux aussi hasher le mot de passe ici avec BCrypt
        clientRepository.save(client);
        return "Client registered successfully";
    }

}
