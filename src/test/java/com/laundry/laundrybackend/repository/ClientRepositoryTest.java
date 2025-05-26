package com.laundry.laundrybackend.repository;

import com.laundry.laundrybackend.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient1;
    private Client testClient2;

    @BeforeEach
    void setUp() {
        // Clean up any existing data
        clientRepository.deleteAll();

        // Create test clients using constructor
        testClient1 = new Client("John Doe", "john.doe@example.com", "password123");
        testClient2 = new Client("Jane Smith", "jane.smith@example.com", "secret456");

        // Persist test data
        testClient1 = clientRepository.save(testClient1);
        testClient2 = clientRepository.save(testClient2);
    }

    @Test
    void findByEmailAndPassword_ShouldReturnClient_WhenEmailAndPasswordMatch() {
        // Given
        String email = "john.doe@example.com";
        String password = "password123";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(email, password);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
        assertThat(result.get().getPassword()).isEqualTo(password);
        assertThat(result.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void findByEmailAndPassword_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        // Given
        String nonExistentEmail = "nonexistent@example.com";
        String password = "password123";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(nonExistentEmail, password);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmailAndPassword_ShouldReturnEmpty_WhenPasswordIsIncorrect() {
        // Given
        String email = "john.doe@example.com";
        String incorrectPassword = "wrongpassword";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(email, incorrectPassword);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmailAndPassword_ShouldReturnEmpty_WhenBothEmailAndPasswordAreIncorrect() {
        // Given
        String incorrectEmail = "wrong@example.com";
        String incorrectPassword = "wrongpassword";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(incorrectEmail, incorrectPassword);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmail_ShouldReturnClient_WhenEmailExists() {
        // Given
        String email = "jane.smith@example.com";

        // When
        Optional<Client> result = clientRepository.findByEmail(email);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
        assertThat(result.get().getName()).isEqualTo("Jane Smith");
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        // Given
        String nonExistentEmail = "notfound@example.com";

        // When
        Optional<Client> result = clientRepository.findByEmail(nonExistentEmail);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void save_ShouldPersistClient_WhenValidClientProvided() {
        // Given
        Client newClient = new Client("Alice Johnson", "alice@example.com", "alicepass");

        // When
        Client savedClient = clientRepository.save(newClient);

        // Then
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo("Alice Johnson");
        assertThat(savedClient.getEmail()).isEqualTo("alice@example.com");
        assertThat(savedClient.getPassword()).isEqualTo("alicepass");

        // Verify it's actually persisted
        Optional<Client> retrievedClient = clientRepository.findById(savedClient.getId());
        assertThat(retrievedClient).isPresent();
        assertThat(retrievedClient.get().getName()).isEqualTo("Alice Johnson");
    }

    @Test
    void save_ShouldThrowException_WhenEmailAlreadyExists() {
        // Given
        Client duplicateEmailClient = new Client("Duplicate User", "john.doe@example.com", "differentpassword");

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            clientRepository.save(duplicateEmailClient);
        });
    }

    @Test
    void findById_ShouldReturnClient_WhenClientExists() {
        // Given
        Long clientId = testClient1.getId();

        // When
        Optional<Client> result = clientRepository.findById(clientId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Doe");
        assertThat(result.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void findById_ShouldReturnEmpty_WhenClientDoesNotExist() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<Client> result = clientRepository.findById(nonExistentId);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void deleteById_ShouldRemoveClient_WhenClientExists() {
        // Given
        Long clientId = testClient1.getId();
        assertThat(clientRepository.findById(clientId)).isPresent();

        // When
        clientRepository.deleteById(clientId);

        // Then
        assertThat(clientRepository.findById(clientId)).isEmpty();
    }

    @Test
    void findAll_ShouldReturnAllClients() {
        // When
        List<Client> allClients = clientRepository.findAll();

        // Then
        assertThat(allClients).hasSize(2);
        assertThat(allClients)
                .extracting(Client::getName)
                .containsExactlyInAnyOrder("John Doe", "Jane Smith");
    }

    @Test
    void count_ShouldReturnCorrectNumberOfClients() {
        // When
        long count = clientRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void findByEmailAndPassword_ShouldHandleNullEmail() {
        // Given
        String password = "password123";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(null, password);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmailAndPassword_ShouldHandleNullPassword() {
        // Given
        String email = "john.doe@example.com";

        // When
        Optional<Client> result = clientRepository.findByEmailAndPassword(email, null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByEmail_ShouldHandleNullEmail() {
        // When
        Optional<Client> result = clientRepository.findByEmail(null);

        // Then
        assertThat(result).isEmpty();
    }
}