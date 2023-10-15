package com.example.epamprojectforcicd.repository;

import com.example.epamprojectforcicd.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
