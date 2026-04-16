package com.example.sala;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository repo;

    public List<Sala> getAll() { return repo.findAll(); }
    
    public List<Sala> getDisponibili() { return repo.findByDisponibileTrue(); }

    public Sala creaSala(Sala sala) {
        Sala s = new Sala();
        s.setNome(sala.getNome());
        s.setCapienza(sala.getCapienza());
        s.setDisponibile(sala.isDisponibile());
        return repo.save(s);
    }

    public Sala aggiornaSala(Long id, Sala nuovaSala) {
        Sala s = repo.findById(id).orElseThrow();
        s.setNome(nuovaSala.getNome());
        s.setCapienza(nuovaSala.getCapienza());
        s.setDisponibile(nuovaSala.isDisponibile());
        return repo.save(s);
    }

    public void eliminaSala(Long id) { repo.deleteById(id); }
}
