package org.example.repository;

import org.example.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, String> {
    boolean findByName(String name);
}
