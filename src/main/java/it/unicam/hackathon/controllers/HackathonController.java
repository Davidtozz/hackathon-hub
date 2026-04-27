package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.builders.HackathonBuilder;
import it.unicam.hackathon.interfaces.ICreazioneHackathon;
import it.unicam.hackathon.interfaces.IElencoHackathon;
import it.unicam.hackathon.interfaces.IIscrizioneHackathon;
import it.unicam.hackathon.repository.HackathonRepository;
import it.unicam.hackathon.service.HackathonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Controller per la gestione degli hackathon.
 * Implementa ICreazioneHackathon, IElencoHackathon, IIscrizioneHackathon.
 */
@Component
public class HackathonController implements ICreazioneHackathon, IElencoHackathon, IIscrizioneHackathon {

    private final HackathonService hackathonService;
    private final HackathonRepository hackathonRepository;
    private final HackathonBuilder hackathonBuilder;

    @Autowired
    public HackathonController(HackathonService hackathonService,
                               HackathonRepository hackathonRepository,
                               HackathonBuilder hackathonBuilder) {
        this.hackathonService = hackathonService;
        this.hackathonRepository = hackathonRepository;
        this.hackathonBuilder = hackathonBuilder;
    }

    /**
     * Crea un hackathon usando il Builder.
     */
    public Hackathon creaHackathon(String nome, String tema, Date inizio, Date fine,
                                   Organizzatore organizzatore) {
        Hackathon h = hackathonBuilder
                .nome(nome)
                .argomento(tema)
                .dataInizio(inizio)
                .dataFine(fine)
                .scadenzaIscrizioni(inizio)
                .organizzatore(organizzatore)
                .build();
        return hackathonService.elaboraCreazioneHackathon(h);
    }

    public void pubblicaHackathon(Integer idHackathon) {
        hackathonService.apriIscrizioni(idHackathon);
    }

    public void iscriviTeam(Integer idTeam, Integer idHackathon) {
        Team team = new Team();
        team.setId(idTeam);
        Hackathon h = new Hackathon();
        h.setId(idHackathon);
        hackathonService.iscriviTeamAdHackathon(team, h);
    }

    // === ICreazioneHackathon ===

    @Override
    public void mostraFormCreazione() { }

    @Override
    public void inserisciDatiHackathon(Hackathon dati) {
        hackathonService.elaboraCreazioneHackathon(dati);
    }

    @Override
    public void confermaCreazione() { }

    @Override
    public void mostraErroreDati() { }

    @Override
    public void notificaCreazioneCompletata() { }

    // === IElencoHackathon ===

    @Override
    public List<Hackathon> visualizzaElencoHackathon() {
        return hackathonRepository.findAll();
    }

    @Override
    public Hackathon visualizzaDettagliHackathon(Integer id) {
        return hackathonRepository.findById(id).orElse(null);
    }

    // === IIscrizioneHackathon ===

    @Override
    public void selezionaHackathon(Integer id) { }

    @Override
    public void confermaIscrizione(Integer idTeam, Integer idHackathon) {
        iscriviTeam(idTeam, idHackathon);
    }

    @Override
    public void mostraErroreIscrizione() { }

    @Override
    public void notificaIscrizioneCompletata() { }
}
