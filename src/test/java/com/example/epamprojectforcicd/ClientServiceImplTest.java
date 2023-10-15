package com.example.epamprojectforcicd;

import com.example.epamprojectforcicd.dto.ClientRequestDto;
import com.example.epamprojectforcicd.model.Client;
import com.example.epamprojectforcicd.repository.ClientRepository;
import com.example.epamprojectforcicd.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClient_ValidRequest() {
        ClientRequestDto clientRequestDto = new ClientRequestDto("username");
        Client expectedClient = new Client();
        expectedClient.setUsername("username");

        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);

        Client createdClient = clientService.createClient(clientRequestDto);

        verify(clientRepository, times(1)).save(any(Client.class));
        assertEquals("username", createdClient.getUsername());
    }

    @Test
    void testCreateClient_InvalidRequest() {
        ClientRequestDto clientRequestDto = new ClientRequestDto(null);

        assertThrows(RuntimeException.class, () -> clientService.createClient(clientRequestDto));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testGetClient_ExistingClient() {
        long clientId = 1L;
        Client expectedClient = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        Optional<Client> retrievedClient = clientService.getClient(clientId);

        verify(clientRepository, times(1)).findById(clientId);
        assertTrue(retrievedClient.isPresent());
        assertEquals(expectedClient, retrievedClient.get());
    }

    @Test
    void testGetClient_NonExistentClient() {
        long clientId = 2L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Optional<Client> retrievedClient = clientService.getClient(clientId);

        verify(clientRepository, times(1)).findById(clientId);
        assertTrue(retrievedClient.isEmpty());
    }

    @Test
    void testDeleteClient_ExistingClient() {
        long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);

        clientService.deleteClient(clientId);

        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void testDeleteClient_NonExistentClient() {
        long clientId = 5L;
        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> clientService.deleteClient(clientId));
        verify(clientRepository, never()).deleteById(clientId);
    }
}

