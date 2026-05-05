package it.unicam.hackathon.interfaces;

/**
 * Interfaccia boundary per l'eliminazione di un Hackathon.
 * Usata dal sequence diagram "Eliminazione Hackathon" della 4a iterazione.
 */
public interface IEliminazioneHackathon {
    void richiedeModuloEliminazione();
    void mostraModuloEliminazione();
    void selezionaHackathonDaEliminare(Integer idHackathon);
    void notificaEliminazione();
    void mostraMessaggioConferma();
}
