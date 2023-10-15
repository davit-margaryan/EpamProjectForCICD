package com.example.epamprojectforcicd.services.impl;

import com.example.epamprojectforcicd.dto.ClientRequestDto;
import com.example.epamprojectforcicd.model.Client;
import com.example.epamprojectforcicd.repository.ClientRepository;
import com.example.epamprojectforcicd.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(ClientRequestDto clientRequestDto) {
        if (!isValidRequest(clientRequestDto)) {
            throw new RuntimeException("User validation failed");
        }
        Client client = new Client();
        client.setUsername(clientRequestDto.getUsername());
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClient(long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private boolean isValidRequest(ClientRequestDto clientRequestDto) {
        return clientRequestDto != null && clientRequestDto.getUsername() != null && !clientRequestDto.getUsername().isEmpty();
    }

}
