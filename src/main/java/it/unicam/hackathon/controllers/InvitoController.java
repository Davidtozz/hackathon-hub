package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.interfaces.IAccettaInvito;
import it.unicam.hackathon.interfaces.IInvito;
import it.unicam.hackathon.service.InvitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione degli inviti. Implementa IInvito e IAccettaInvito.
 */
@Component
public class InvitoController implements IInvito, IAccettaInvito {

    private final InvitoService invitoService;

    @Autowired
    public InvitoController(InvitoService invitoService) {
        this.invitoService = invitoService;
    }

    public void inviaInvito(String nome, String cognome, String email, Integer idTeam) {
        Invito invito = invitoService.elaboraInvito(nome, cognome, email, idTeam);
        invitoService.inviaEmailInvito(invito);
    }

    public void rispondiInvito(Integer idInvito, boolean accetta) {
        invitoService.rispondiInvito(idInvito, accetta);
    }

    // === IInvito ===

    @Override
    public void richiedeInvioInvito() { }

    @Override
    public void mostraFormInvito() { }

    @Override
    public void inserisciDatiUtente(String nome, String cognome, String email, Integer idTeam) {
        inviaInvito(nome, cognome, email, idTeam);
    }

    @Override
    public void mostraErroreDati() { }

    @Override
    public void notificaInvioInvito() { }

    // === IAccettaInvito ===

    @Override
    public List<Invito> visualizzaInviti(Integer idUtente) {
        return invitoService.visualizzaInvitiByUtente(idUtente);
    }

    @Override
    public void selezionaInvito(Integer idInvito) { }

    @Override
    public void confermaAccettazione() { }

    @Override
    public void confermaRifiuto() { }
}
