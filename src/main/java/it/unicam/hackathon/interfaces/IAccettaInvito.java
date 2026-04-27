package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Invito;

import java.util.List;

/**
 * Interfaccia boundary per accettare/rifiutare inviti.
 */
public interface IAccettaInvito {
    List<Invito> visualizzaInviti(Integer idUtente);
    void selezionaInvito(Integer idInvito);
    void confermaAccettazione();
    void confermaRifiuto();
}
