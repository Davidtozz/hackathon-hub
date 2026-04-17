package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.TeamLeader;
import it.unicam.hackathon.actors.UtenteSenzaTeam;

/**
 * Responsabile solo della creazione del team e della promozione del creatore a TeamLeader.
 */
public class TeamController {

    public static TeamLeader creaTeam(String nomeTeam, UtenteSenzaTeam creatore) {
        TeamLeader teamLeader = new TeamLeader(creatore);
        Team team = new Team(nomeTeam, teamLeader);
        teamLeader.setTeamDiAppartenenza(team);
        return teamLeader;
    }

    public static create()
}