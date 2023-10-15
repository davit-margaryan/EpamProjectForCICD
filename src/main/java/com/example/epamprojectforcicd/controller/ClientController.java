package com.example.epamprojectforcicd.controller;

import com.example.epamprojectforcicd.dto.ClientRequestDto;
import com.example.epamprojectforcicd.model.Client;
import com.example.epamprojectforcicd.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody ClientRequestDto clientRequestDto) {
        Client createdClient = clientService.createClient(clientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClient(@PathVariable Long clientId) {
        Optional<Client> client = clientService.getClient(clientId);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        try {
            clientService.deleteClient(clientId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

