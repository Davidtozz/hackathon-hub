package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.TeamLeader;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.interfaces.ICreazioneTeam;
import it.unicam.hackathon.interfaces.IEliminazioneTeam;
import it.unicam.hackathon.interfaces.ILasciaGruppo;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione dei team.
 * Implementa le interfacce boundary definite nei sequence diagram:
 *   - 1a iterazione: ICreazioneTeam
 *   - 4a iterazione: IEliminazioneTeam, ILasciaGruppo
 */
@Component
public class TeamController implements ICreazioneTeam, IEliminazioneTeam, ILasciaGruppo {

    private final TeamService teamService;
    private final UtenteRepository utenteRepository;

    @Autowired
    public TeamController(TeamService teamService, UtenteRepository utenteRepository) {
        this.teamService = teamService;
        this.utenteRepository = utenteRepository;
    }

    /**
     * Crea un nuovo team promuovendo un UtenteSenzaTeam a TeamLeader.
     */
    public Team creaTeam(String nomeTeam, UtenteSenzaTeam creatore) {
        TeamLeader leader = new TeamLeader(creatore);
        Team team = teamService.creaNuovoTeam(nomeTeam, leader);
        utenteRepository.save(leader);
        return team;
    }

    // ============================================================
    // === METODI PUBBLICI AGGIUNTI NELLA 4a ITERAZIONE         ===
    // ============================================================

    /**
     * Restituisce i team gestiti da un TeamLeader.
     * Sequence diagram: "Eliminazione Team".
     */
    public List<Team> getTeamsByLeader(Integer idLeader) {
        return teamService.ottieniTeamsPerLeader(idLeader);
    }

    /**
     * Fa abbandonare un team a un suo membro.
     * Sequence diagram: "Lascia Gruppo".
     * Implementa anche ILasciaGruppo.abbandonaGruppo(Integer).
     */
    @Override
    public void abbandonaGruppo(Integer idMembro) {
        teamService.rimuoviMembroDaTeam(idMembro);
    }

    // ============================================================
    // === Implementazioni interfacce boundary                  ===
    // ============================================================

    // === ICreazioneTeam ===

    @Override
    public void mostraModaleCreazioneTeam() { }

    @Override
    public void inserisciInfoTeam(String nomeTeam) { }

    @Override
    public void mostraModuloInviti() { }

    @Override
    public void inserisciDatiMembri(List<BaseUtente> membri) { }

    @Override
    public void confermaCreazione() { }

    @Override
    public void notificaDatiGiaPresenti() { }

    @Override
    public void mostraMessaggioSuccesso() { }

    // === IEliminazioneTeam (4a iterazione) ===

    @Override
    public void mostraTeams() { }

    @Override
    public void visualizzaElencoTeams(List<Team> teams) { }

    @Override
    public void eliminaTeam(Integer idTeam) {
        teamService.eliminaTeam(idTeam);
    }

    @Override
    public void notificaEliminazione() { }

    @Override
    public void mostraMessaggioConferma() { }

    // === ILasciaGruppo (4a iterazione) ===

    @Override
    public void richiediAbbandonoGruppo() { }

    @Override
    public void mostraConfermaAbbandono() { }

    // abbandonaGruppo(Integer idMembro) e' gia' implementato sopra come metodo
    // pubblico: la stessa firma soddisfa anche l'override di ILasciaGruppo.

    @Override
    public void notificaAbbandono() { }

    // mostraMessaggioConferma() e' gia' implementato in IEliminazioneTeam:
    // la stessa implementazione vale anche qui (stessa firma).
}
