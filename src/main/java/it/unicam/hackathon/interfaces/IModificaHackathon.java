package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Hackathon;

import java.util.List;

/**
 * Interfaccia boundary per la modifica di un Hackathon esistente.
 * Usata dal sequence diagram "Modifica Hackathon" della 4a iterazione.
 */
public interface IModificaHackathon {
    void mostraElencoHackathon(List<Hackathon> hackathons);
    void mostraModuloModifica(Hackathon h);
    void notificaModifica();
    void mostraMessaggioErrore();
}
