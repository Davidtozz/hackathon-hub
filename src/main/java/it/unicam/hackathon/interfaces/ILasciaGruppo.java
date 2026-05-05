package it.unicam.hackathon.interfaces;

/**
 * Interfaccia boundary per l'abbandono di un team da parte di un MembroDelTeam.
 * Usata dal sequence diagram "Lascia Gruppo" della 4a iterazione.
 */
public interface ILasciaGruppo {
    void richiediAbbandonoGruppo();
    void mostraConfermaAbbandono();
    void abbandonaGruppo(Integer idMembro);
    void notificaAbbandono();
    void mostraMessaggioConferma();
}
