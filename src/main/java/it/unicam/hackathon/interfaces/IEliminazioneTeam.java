package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Team;

import java.util.List;

/**
 * Interfaccia boundary per l'eliminazione di un Team da parte del TeamLeader.
 * Usata dal sequence diagram "Eliminazione Team" della 4a iterazione.
 */
public interface IEliminazioneTeam {
    void mostraTeams();
    void visualizzaElencoTeams(List<Team> teams);
    void eliminaTeam(Integer idTeam);
    void notificaEliminazione();
    void mostraMessaggioConferma();
}
