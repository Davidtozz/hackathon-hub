package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO per la registrazione di un utente via REST.
 */
@Data
@NoArgsConstructor
public class UtenteRequest {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String cellulare;
    private Date dataIscrizione;
    private String ruolo;  // "UTENTE_SENZA_TEAM", "MEMBRO_DEL_TEAM", "ORGANIZZATORE", "GIUDICE", "MENTORE"
}
