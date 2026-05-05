package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Hackathon;

import java.util.List;

/**
 * Interfaccia boundary per la visualizzazione degli hackathon di un utente.
 * Usata dal sequence diagram "Visualizza I Miei Hackathon" della 4a iterazione.
 */
public interface IVisualizzaHackathon {
    void richiediVisualizzareIMieiHackathon();
    void mostraElencoHackathon(List<Hackathon> lista);
    void mostraErroreNessunHackathon();
}
