package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.BaseUtente;

/**
 * Interfaccia boundary per la visualizzazione del profilo utente.
 */
public interface IVisualizzaProfilo {
    BaseUtente visualizzaProfilo(Integer id);
    void mostraDatiProfilo(BaseUtente u);
}
