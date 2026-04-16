package com.example.sala;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SalaController {
    private SalaService salaService;

@GetMapping
    public List<Sala> getAll() { return salaService.getAll(); }

    @GetMapping("/disponibili")
    public List<Sala> getDisponibili() { return salaService.getDisponibili(); }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Sala> create(@Valid @RequestBody Sala sala) {
        return ResponseEntity.ok(salaService.creaSala(sala));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Sala> update(@PathVariable Long id, @Valid @RequestBody Sala sala) {
        return ResponseEntity.ok(salaService.aggiornaSala(id, sala));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.eliminaSala(id);
        return ResponseEntity.noContent().build();
    }
}
