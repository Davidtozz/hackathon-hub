package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.BaseUtente;

/**
 * Interfaccia boundary per la registrazione di un nuovo utente.
 */
public interface IRegistrazione {
    void mostraModuloRegistrazione();
    void inserimentoDati(BaseUtente dati);
    void annullaRegistrazione();
    void reindirizzaAllaHome();
}
