package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe astratta base per tutti gli utenti del sistema.
 * Contiene gli attributi comuni (anagrafica).
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class BaseUtente extends BaseAttore {
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;
    protected String cellulare;
    protected Date dataIscrizione = new Date();
}
