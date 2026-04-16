package com.example.esercizio_coworking.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository {
    Optional<Utente> findByUsername(String Username);
}