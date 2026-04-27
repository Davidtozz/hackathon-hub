package it.unicam.hackathon.interfaces;

import it.unicam.hackathon.actors.BaseUtente;

import java.util.List;

/**
 * Interfaccia boundary per il flusso di creazione di un team.
 */
public interface ICreazioneTeam {
    void mostraModaleCreazioneTeam();
    void inserisciInfoTeam(String nomeTeam);
    void mostraModuloInviti();
    void inserisciDatiMembri(List<BaseUtente> membri);
    void confermaCreazione();
    void notificaDatiGiaPresenti();
    void mostraMessaggioSuccesso();
}
