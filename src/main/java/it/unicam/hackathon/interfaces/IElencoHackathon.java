package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.Hackathon;

import java.util.List;

/**
 * Interfaccia boundary per visualizzare l'elenco degli hackathon disponibili.
 */
public interface IElencoHackathon {
    List<Hackathon> visualizzaElencoHackathon();
    Hackathon visualizzaDettagliHackathon(Integer id);
}
