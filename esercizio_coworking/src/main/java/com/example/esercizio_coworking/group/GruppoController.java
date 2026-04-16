package com.example.esercizio_coworking.group;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/gruppi")
@RequiredArgsConstructor
public class GruppoController {
    private final GruppoService gruppoService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Gruppo> getAll() {
        return gruppoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gruppo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gruppoService.getById(id));
    }

    @GetMapping("/manager/{managerId}")
    public List<Gruppo> getByManager(@PathVariable Long managerId) {
        return gruppoService.getByManager(managerId);
    }

    @PostMapping
    public ResponseEntity<Gruppo> create(@Valid @RequestBody Gruppo gruppo) {
        return ResponseEntity.ok(gruppoService.creaGruppo(gruppo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gruppo> update(@PathVariable Long id, @Valid @RequestBody Gruppo gruppo) {
        return ResponseEntity.ok(gruppoService.aggiornaGruppo(id, gruppo));
    }

    @PutMapping("/{id}/aggiungi-utente")
    public ResponseEntity<Gruppo> aggiungiUtente(@PathVariable Long id, @RequestParam Long utenteId) {
        return ResponseEntity.ok(gruppoService.aggiungiUtente(id, utenteId));
    }

    @PutMapping("/{id}/rimuovi-utente")
    public ResponseEntity<Gruppo> rimuoviUtente(@PathVariable Long id, @RequestParam Long utenteId) {
        return ResponseEntity.ok(gruppoService.rimuoviUtente(id, utenteId));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gruppoService.eliminaGruppo(id);
        return ResponseEntity.noContent().build();
    }
}
