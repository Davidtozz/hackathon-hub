package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.TeamLeader;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.interfaces.ICreazioneTeam;
import it.unicam.hackathon.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione dei team. Implementa l'interfaccia boundary ICreazioneTeam.
 */
@Component
public class TeamController implements ICreazioneTeam {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Crea un nuovo team promuovendo un UtenteSenzaTeam a TeamLeader.
     */
    public Team creaTeam(String nomeTeam, UtenteSenzaTeam creatore) {
        TeamLeader leader = new TeamLeader(creatore);
        return teamService.creaNuovoTeam(nomeTeam, leader);
    }

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
}
