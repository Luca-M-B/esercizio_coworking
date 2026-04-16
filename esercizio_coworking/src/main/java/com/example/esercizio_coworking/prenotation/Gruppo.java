package com.example.esercizio_coworking.prenotation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

import com.example.esercizio_coworking.user.Utente;

@Data
@Entity
public class Gruppo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private Utente manager;

    @ManyToMany
    @JoinTable(name = "gruppo_utenti", joinColumns = @JoinColumn(name = "gruppo_id"), inverseJoinColumns = @JoinColumn(name = "utente_id"))
    private List<Utente> utenti;
}