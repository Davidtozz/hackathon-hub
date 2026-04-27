package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Sottomissione;

/**
 * Interfaccia boundary per inviare/aggiornare una sottomissione.
 */
public interface ISottomissione {
    void mostraFormSottomissione();
    void inviaSottomissione(String link);
    void aggiornaSottomissione(Integer id, String link);
    Sottomissione visualizzaSottomissione(Integer id);
}
