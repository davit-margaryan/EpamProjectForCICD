package com.example.epamprojectforcicd.services;

import com.example.epamprojectforcicd.dto.ClientRequestDto;
import com.example.epamprojectforcicd.model.Client;

import java.util.Optional;

public interface ClientService {

    Client createClient(ClientRequestDto clientRequestDto);

    Optional<Client> getClient(long id);

    void deleteClient(long id);
}
