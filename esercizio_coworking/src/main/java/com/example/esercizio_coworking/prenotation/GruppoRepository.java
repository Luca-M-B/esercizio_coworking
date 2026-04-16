package com.example.esercizio_coworking.prenotation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.esercizio_coworking.user.Utente;

public interface GruppoRepository extends JpaRepository <Gruppo, Long>{
    List<Utente> findByGruppo(Gruppo gruppo);
}
