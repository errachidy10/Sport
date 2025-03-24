package org.example.sport.controllers;

import org.example.sport.entite.Utilisateur;
import org.example.sport.services.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UtilisateurController.class)
@AutoConfigureMockMvc
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UtilisateurService utilisateurService;

    @Test
    public void testInscrireUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMotDePasse("password");

        when(utilisateurService.inscrireUtilisateur(any(Utilisateur.class))).thenReturn(utilisateur);

        mockMvc.perform(post("/api/utilisateurs/inscription")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"motDePasse\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testObtenirUtilisateurParEmail() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");

        when(utilisateurService.trouverParEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        mockMvc.perform(get("/api/utilisateurs/{email}", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testMettreAJourUtilisateur() throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMotDePasse("password");

        when(utilisateurService.mettreAJourUtilisateur(utilisateur.getId(),any(Utilisateur.class))).thenReturn(utilisateur);

        mockMvc.perform(put("/api/utilisateurs/mise-a-jour")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"motDePasse\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testSupprimerUtilisateur() throws Exception {
        doNothing().when(utilisateurService).supprimerUtilisateur(1L);

        mockMvc.perform(delete("/api/utilisateurs/suppression/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenirTousLesUtilisateurs() throws Exception {
        when(utilisateurService.obtenirTousLesUtilisateurs()).thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/api/utilisateurs/tous")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
