package com.example.esercizio_coworking.prenotation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.esercizio_coworking.user.Utente;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository repo;

    public List<Prenotazione> getPrenotazioni(Utente utente) {
        if (utente.getRuolo().equals("ADMIN")) {
            return repo.findAll();
        }

        if (utente.getRuolo().equals("MANAGER")) {
            return repo.findByCreataDa(utente);
        }

        return repo.findByCreataDa(utente);
    }

    public Prenotazione getById(Long id, Utente utente) {
        Prenotazione p = repo.findById(id).orElseThrow();

        if (utente.getRuolo().equals("ADMIN")) {
            return p;
        }

        if (utente.getRuolo().equals("MANAGER") && p.getCreataDa().equals(utente)) {
            return p;
        }

        if (p.getCreataDa().equals(utente)) {
            return p;
        }

        throw new RuntimeException("Non hai accesso");
    }

    public Prenotazione creaPrenotazione(Utente utente) {
        Prenotazione p = new Prenotazione();
        p.setCreataDa(utente);
        p.setStato(StatoPrenotazione.ATTIVA);
        p.setDataInizio(LocalDateTime.now());
        p.setDataFine(LocalDateTime.now().plusHours(1));

        return repo.save(p);
    }

    public void eliminaPrenotazione(Long id, Utente utente) {
        Prenotazione p = getById(id, utente);
        repo.delete(p);
    }

    public Prenotazione aggiornaStato(Long id, StatoPrenotazione stato, Utente utente) {
        Prenotazione p = getById(id, utente);
        p.setStato(stato);
        return repo.save(p);
    }
}
