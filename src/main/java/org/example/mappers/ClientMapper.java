package org.example.mappers;

import org.example.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client mapFromDto(String name, String email) {
        return new Client(name, email);
    }
}
