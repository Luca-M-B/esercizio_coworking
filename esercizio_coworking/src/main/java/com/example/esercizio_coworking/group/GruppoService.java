package com.example.esercizio_coworking.prenotation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.esercizio_coworking.user.Utente;
import com.example.esercizio_coworking.user.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GruppoService {
    private final GruppoRepository gruppoRepository;
    private final UtenteRepository utenteRepository;

    public List<Gruppo> getAll() { return gruppoRepository.findAll(); }
 
    public Gruppo getById(Long id) {
        return gruppoRepository.findById(id).orElseThrow();
    }

    public List<Gruppo> getByManager(Long managerId) {
        Utente manager = utenteRepository.findById(managerId).orElseThrow();
        return gruppoRepository.findByManager(manager);
    }

    public Gruppo creaGruppo(Gruppo gruppo) {
        Gruppo g = new Gruppo();
        g.setNome(gruppo.getNome());
        g.setManager(gruppo.getManager());
        g.setUtenti(gruppo.getUtenti());
        return gruppoRepository.save(g);
    }

    public Gruppo aggiornaGruppo(Long id, Gruppo nuovoGruppo) {
        Gruppo g = gruppoRepository.findById(id).orElseThrow();
        g.setNome(nuovoGruppo.getNome());
        g.setManager(nuovoGruppo.getManager());
        g.setUtenti(nuovoGruppo.getUtenti());
        return gruppoRepository.save(g);
    }

    public Gruppo aggiungiUtente(Long gruppoId, Long utenteId) {
        Gruppo g = gruppoRepository.findById(gruppoId).orElseThrow();
        Utente u = utenteRepository.findById(utenteId).orElseThrow();
        if (!g.getUtenti().contains(u)) {
            g.getUtenti().add(u);
        }
        return gruppoRepository.save(g);
    }

    public Gruppo rimuoviUtente(Long gruppoId, Long utenteId) {
        Gruppo g = gruppoRepository.findById(gruppoId).orElseThrow();
        g.getUtenti().removeIf(u -> u.getId().equals(utenteId));
        return gruppoRepository.save(g);
    }

    public void eliminaGruppo(Long id) {
        gruppoRepository.deleteById(id);
    }
}
