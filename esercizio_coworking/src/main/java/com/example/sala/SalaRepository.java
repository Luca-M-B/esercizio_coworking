package com.example.sala;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.esercizio_coworking.user.Utente;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long>{
    List<Sala> findByDisponibileTrue();
}
