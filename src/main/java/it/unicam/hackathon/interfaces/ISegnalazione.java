package it.unicam.hackathon.interfaces;

/**
 * Interfaccia boundary per creare una segnalazione verso un team.
 */
public interface ISegnalazione {
    void richiediFormSegnalazione();
    void mostraForm();
    void compilaForm(Integer idTeam, String nomeTeam, String descrizione, String motivazione);
    void mostraErrore();
    void notificaSuccesso();
}
