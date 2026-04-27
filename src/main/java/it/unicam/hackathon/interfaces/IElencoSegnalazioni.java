package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Segnalazione;

import java.util.List;

/**
 * Interfaccia boundary per visualizzare l'elenco delle segnalazioni.
 */
public interface IElencoSegnalazioni {
    List<Segnalazione> visualizzaElencoSegnalazioni();
    Segnalazione visualizzaDettaglioSegnalazione(Integer id);
}
