package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Segnalazione;
import it.unicam.hackathon.interfaces.IElencoSegnalazioni;
import it.unicam.hackathon.interfaces.ISegnalazione;
import it.unicam.hackathon.repository.SegnalazioneRepository;
import it.unicam.hackathon.service.SegnalazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione delle segnalazioni.
 * Implementa ISegnalazione e IElencoSegnalazioni.
 */
@Component
public class ControllerSegnalazione implements ISegnalazione, IElencoSegnalazioni {

    private final SegnalazioneService segnalazioneService;
    private final SegnalazioneRepository segnalazioneRepository;

    @Autowired
    public ControllerSegnalazione(SegnalazioneService segnalazioneService,
                                  SegnalazioneRepository segnalazioneRepository) {
        this.segnalazioneService = segnalazioneService;
        this.segnalazioneRepository = segnalazioneRepository;
    }

    public List<Segnalazione> visualizzaSegnalazioni() {
        return segnalazioneService.getSegnalazioni();
    }

    public void gestisciSegnalazione(Integer idSegnalazione) {
        // delegato al service
    }

    // === ISegnalazione ===

    @Override
    public void richiediFormSegnalazione() { }

    @Override
    public void mostraForm() { }

    @Override
    public void compilaForm(Integer idTeam, String nomeTeam, String descrizione, String motivazione) {
        // delegato al SegnalazioneService
    }

    @Override
    public void mostraErrore() { }

    @Override
    public void notificaSuccesso() { }

    // === IElencoSegnalazioni ===

    @Override
    public List<Segnalazione> visualizzaElencoSegnalazioni() {
        return segnalazioneRepository.findAll();
    }

    @Override
    public Segnalazione visualizzaDettaglioSegnalazione(Integer id) {
        return segnalazioneRepository.findById(id).orElse(null);
    }
}
