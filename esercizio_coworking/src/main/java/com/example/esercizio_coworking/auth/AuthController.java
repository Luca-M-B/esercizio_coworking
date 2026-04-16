package com.example.esercizio_coworking.auth;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.esercizio_coworking.user.CustomUserDetailsService;
import com.example.esercizio_coworking.model.Utente;
import com.example.esercizio_coworking.user.UtenteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtService.generateToken(user);
        String refreshToken = UUID.randomUUID().toString();

        Utente u = utenteRepository.findByUsername(user.getUsername()).get();
        u.setRefreshToken(refreshToken);
        utenteRepository.save(u);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        Utente u = utenteRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token non valido"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(u.getUsername());
        String newToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(newToken, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        utenteRepository.findByRefreshToken(refreshToken)
                .ifPresent(u -> {
                    u.setRefreshToken(null);
                    utenteRepository.save(u);
                });

        return ResponseEntity.ok("Logout effettuato.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request) {

        if (utenteRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username già registrato");
        }

        Utente nuovo = new Utente();
        nuovo.setUsername(request.getUsername());
        nuovo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuovo.setRuolo(request.getRuolo());

        utenteRepository.save(nuovo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registrazione completata con successo");
    }
}