package it.unicam.hackathon.interfaces;

/**
 * Interfaccia boundary per l'invio di un invito a un team.
 */
public interface IInvito {
    void richiedeInvioInvito();
    void mostraFormInvito();
    void inserisciDatiUtente(String nome, String cognome, String email, Integer idTeam);
    void mostraErroreDati();
    void notificaInvioInvito();
}
