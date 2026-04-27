package it.unicam.hackathon.interfaces;

/**
 * Interfaccia boundary per l'iscrizione di un team a un hackathon.
 */
public interface IIscrizioneHackathon {
    void selezionaHackathon(Integer id);
    void confermaIscrizione(Integer idTeam, Integer idHackathon);
    void mostraErroreIscrizione();
    void notificaIscrizioneCompletata();
}
