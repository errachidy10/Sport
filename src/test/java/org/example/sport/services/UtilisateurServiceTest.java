package org.example.sport.services;

import org.example.sport.entite.Utilisateur;
import org.example.sport.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Correctement placé ici
    }

    @Test
    public void testInscrireUtilisateur() {
        // GIVEN : Un utilisateur avec un mot de passe non encodé
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMotDePasse("password");

        // WHEN : On encode le mot de passe et sauvegarde l'utilisateur
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = utilisateurService.inscrireUtilisateur(utilisateur);

        // THEN : Le mot de passe doit être encodé
        assertEquals("encodedPassword", result.getMotDePasse());
    }

    @Test
    public void testTrouverParEmail() {
        // GIVEN : Un utilisateur enregistré avec un email donné
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");

        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        // WHEN : On cherche l'utilisateur
        Optional<Utilisateur> result = utilisateurService.trouverParEmail("test@example.com");

        // THEN : L'utilisateur doit être trouvé et avoir le bon email
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }
}
