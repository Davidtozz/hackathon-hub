package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.interfaces.ISottomissione;
import it.unicam.hackathon.repository.SottomissioneRepository;
import it.unicam.hackathon.service.SottomissioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller per la gestione delle sottomissioni. Implementa ISottomissione.
 */
@Component
public class ControllerSottomissione implements ISottomissione {

    private final SottomissioneService sottomissioneService;
    private final SottomissioneRepository sottomissioneRepository;

    @Autowired
    public ControllerSottomissione(SottomissioneService sottomissioneService,
                                   SottomissioneRepository sottomissioneRepository) {
        this.sottomissioneService = sottomissioneService;
        this.sottomissioneRepository = sottomissioneRepository;
    }

    public List<Sottomissione> visualizzaElencoSottomissioni() {
        return sottomissioneRepository.findAll();
    }

    // === ISottomissione ===

    @Override
    public void mostraFormSottomissione() { }

    @Override
    public void inviaSottomissione(String link) {
        // il team viene passato nel flow reale; qui solo placeholder
    }

    @Override
    public void aggiornaSottomissione(Integer id, String link) {
        sottomissioneService.aggiornaSottomissione(id, link);
    }

    @Override
    public Sottomissione visualizzaSottomissione(Integer id) {
        return sottomissioneRepository.findById(id).orElse(null);
    }
}
