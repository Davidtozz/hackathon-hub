package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Hackathon;

/**
 * Interfaccia boundary per la creazione di un nuovo Hackathon.
 */
public interface ICreazioneHackathon {
    void mostraFormCreazione();
    void inserisciDatiHackathon(Hackathon dati);
    void confermaCreazione();
    void mostraErroreDati();
    void notificaCreazioneCompletata();
}
