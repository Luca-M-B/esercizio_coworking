package com.example.esercizio_coworking.prenotation;

import com.example.esercizio_coworking.user.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @PostMapping
    public ResponseEntity<Prenotazione> creaPrenotazione(
            Authentication authentication) {
        Utente utente = (Utente) authentication.getPrincipal();

        Prenotazione prenotazione = prenotazioneService.creaPrenotazione(utente);

        return ResponseEntity.ok(prenotazione);
    }

    @GetMapping
    public ResponseEntity<List<Prenotazione>> getPrenotazioni(Authentication authentication) {
        Utente utente = (Utente) authentication.getPrincipal();

        List<Prenotazione> lista = prenotazioneService.getPrenotazioni(utente);

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prenotazione> getPrenotazioneById(
            @PathVariable Long id,
            Authentication authentication) {
        Utente utente = (Utente) authentication.getPrincipal();

        Prenotazione prenotazione = prenotazioneService.getById(id, utente);

        return ResponseEntity.ok(prenotazione);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaPrenotazione(
            @PathVariable Long id,
            Authentication authentication) {
        Utente utente = (Utente) authentication.getPrincipal();

        prenotazioneService.eliminaPrenotazione(id, utente);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stato")
    public ResponseEntity<Prenotazione> aggiornaStato(
            @PathVariable Long id,
            @RequestParam StatoPrenotazione stato,
            Authentication authentication) {
        Utente utente = (Utente) authentication.getPrincipal();

        Prenotazione prenotazione = prenotazioneService.aggiornaStato(id, stato, utente);

        return ResponseEntity.ok(prenotazione);
    }
}