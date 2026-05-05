package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Team;

import java.util.List;

/**
 * Interfaccia boundary per l'assegnazione del vincitore di un Hackathon.
 * Usata dal sequence diagram "Assegna Vincitore" della 4a iterazione.
 */
public interface IAssegnaVincitore {
    void mostraTeamPartecipanti(List<Team> teams);
    void assegnaVincitore(Integer idHackathon, Integer idTeam);
    void notificaAssegnazione();
    void mostraMessaggioConferma();
}
