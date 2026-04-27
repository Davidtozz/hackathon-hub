package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.interfaces.IValutazione;
import it.unicam.hackathon.repository.SottomissioneRepository;
import it.unicam.hackathon.service.ValutazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione delle valutazioni. Implementa IValutazione.
 */
@Component
public class ControllerValutazione implements IValutazione {

    private final ValutazioneService valutazioneService;
    private final SottomissioneRepository sottomissioneRepository;

    @Autowired
    public ControllerValutazione(ValutazioneService valutazioneService,
                                 SottomissioneRepository sottomissioneRepository) {
        this.valutazioneService = valutazioneService;
        this.sottomissioneRepository = sottomissioneRepository;
    }

    public Sottomissione selezionaSottomissione(Integer idSottomissione) {
        return sottomissioneRepository.findById(idSottomissione).orElse(null);
    }

    // === IValutazione ===

    @Override
    public List<Sottomissione> visualizzaSottomissioniAssegnate() {
        return sottomissioneRepository.findAll();
    }


    @Override
    public void inserisciValutazione(double punteggio, String commento) {
        // la valutazione viene creata dal service con giudice+sottomissione+punteggio
    }

    @Override
    public void confermaValutazione() { }
}
