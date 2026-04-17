package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.inviti.IAccettaInvito;
import it.unicam.hackathon.repository.InvitoRepository;

import java.util.List;

public final class InvitoController implements IAccettaInvito {

    private InvitoRepository invitoRepository;

    public InvitoController(InvitoRepository invitoRepository) {
        this.invitoRepository = invitoRepository;
    }

    public List<Invito> getInviti(Integer idUtente) {
        return null;
    }

    @Override
    public List<Invito> recuperaInviti(Integer utenteId) {
        return List.of();
    }

    @Override
    public List<Invito> visualizzaInviti(Integer utenteId) {
        return List.of();
    }
}
