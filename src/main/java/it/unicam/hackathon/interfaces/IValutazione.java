package it.unicam.hackathon.interfaces;

import java.util.List;

import it.unicam.hackathon.actors.Sottomissione;

/**
 * Interfaccia boundary per valutare una sottomissione.
 */
public interface IValutazione {
    List<Sottomissione> visualizzaSottomissioniAssegnate();
    Sottomissione selezionaSottomissione(Integer id);
    void inserisciValutazione(double punteggio, String commento);
    void confermaValutazione();
}
