package it.unicam.hackathon.controllers;

import it.unicam.hackathon.models.Team;
import it.unicam.hackathon.models.TeamLeader;
import it.unicam.hackathon.models.UtenteSenzaTeam;

/**
 * Responsabile solo della creazione del team e della promozione del creatore a TeamLeader.
 */
public class TeamController {

    public Team createTeam(String nomeTeam, UtenteSenzaTeam creatore) {
        TeamLeader teamLeader = promoteToTeamLeader(creatore);
        Team team = new Team(nomeTeam, teamLeader);
        teamLeader.setTeamDiAppartenenza(team);
        return team;
    }

    private TeamLeader promoteToTeamLeader(UtenteSenzaTeam utente) {
        return new TeamLeader(utente);
    }
}