package com.example.esercizio_coworking.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.esercizio_coworking.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByRefreshToken(String refreshToken);
}