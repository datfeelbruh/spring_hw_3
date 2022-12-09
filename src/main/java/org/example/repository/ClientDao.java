package org.example.repository;

import org.example.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClientDao extends JpaRepository<Client, String> {
    @Query(value = "select c.email from client c where c.email = :email", nativeQuery = true)
    String  findByEmail(@Param("email") String email);
}
