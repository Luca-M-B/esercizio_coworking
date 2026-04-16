package com.example.esercizio_coworking.prenotation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.esercizio_coworking.user.Utente;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByDisponibileTrue();

    List<Prenotazione> findByCreataDa(Utente utente);
}