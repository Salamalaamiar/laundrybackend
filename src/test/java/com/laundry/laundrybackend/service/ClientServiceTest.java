package com.laundry.laundrybackend.service;

import com.laundry.laundrybackend.model.Client;
import com.laundry.laundrybackend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client();
        testClient.setEmail("test@example.com");
        testClient.setPassword("password123");
        // Set other client properties as needed
        // Note: ID is auto-generated, so we don't set it manually
    }

    @Test
    void authenticate_WithValidCredentials_ShouldReturnClient() {
        // Given
        String email = "test@example.com";
        String password = "password123";
        when(clientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.of(testClient));

        // When
        Optional<Client> result = clientService.authenticate(email, password);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
        assertThat(result.get().getPassword()).isEqualTo(password);
        verify(clientRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void authenticate_WithInvalidCredentials_ShouldReturnEmpty() {
        // Given
        String email = "wrong@example.com";
        String password = "wrongpassword";
        when(clientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.empty());

        // When
        Optional<Client> result = clientService.authenticate(email, password);

        // Then
        assertThat(result).isEmpty();
        verify(clientRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void authenticate_WithNullEmail_ShouldReturnEmpty() {
        // Given
        String email = null;
        String password = "password123";
        when(clientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.empty());

        // When
        Optional<Client> result = clientService.authenticate(email, password);

        // Then
        assertThat(result).isEmpty();
        verify(clientRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void authenticate_WithNullPassword_ShouldReturnEmpty() {
        // Given
        String email = "test@example.com";
        String password = null;
        when(clientRepository.findByEmailAndPassword(email, password))
                .thenReturn(Optional.empty());

        // When
        Optional<Client> result = clientService.authenticate(email, password);

        // Then
        assertThat(result).isEmpty();
        verify(clientRepository).findByEmailAndPassword(email, password);
    }

    @Test
    void registerClient_WithNewEmail_ShouldReturnSuccessMessage() {
        // Given
        Client newClient = new Client();
        newClient.setEmail("new@example.com");
        newClient.setPassword("newpassword");

        when(clientRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class)))
                .thenReturn(newClient);

        // When
        String result = clientService.registerClient(newClient);

        // Then
        assertThat(result).isEqualTo("Client registered successfully");
        verify(clientRepository).findByEmail("new@example.com");
        verify(clientRepository).save(newClient);
    }

    @Test
    void registerClient_WithExistingEmail_ShouldReturnErrorMessage() {
        // Given
        Client existingClient = new Client();
        existingClient.setEmail("existing@example.com");
        existingClient.setPassword("password");

        when(clientRepository.findByEmail("existing@example.com"))
                .thenReturn(Optional.of(testClient));

        // When
        String result = clientService.registerClient(existingClient);

        // Then
        assertThat(result).isEqualTo("Email already in use");
        verify(clientRepository).findByEmail("existing@example.com");
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void registerClient_WithNullClient_ShouldHandleGracefully() {
        // Given
        Client nullClient = new Client();
        nullClient.setEmail(null);

        when(clientRepository.findByEmail(null))
                .thenReturn(Optional.empty());

        // When
        String result = clientService.registerClient(nullClient);

        // Then
        assertThat(result).isEqualTo("Client registered successfully");
        verify(clientRepository).findByEmail(null);
        verify(clientRepository).save(nullClient);
    }

    @Test
    void registerClient_RepositoryThrowsException_ShouldPropagateException() {
        // Given
        Client newClient = new Client();
        newClient.setEmail("test@example.com");

        when(clientRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        try {
            clientService.registerClient(newClient);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Database error");
        }

        verify(clientRepository).findByEmail("test@example.com");
        verify(clientRepository).save(newClient);
    }
}