package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.builders.HackathonBuilder;
import it.unicam.hackathon.interfaces.IAssegnaVincitore;
import it.unicam.hackathon.interfaces.ICreazioneHackathon;
import it.unicam.hackathon.interfaces.IElencoHackathon;
import it.unicam.hackathon.interfaces.IEliminazioneHackathon;
import it.unicam.hackathon.interfaces.IIscrizioneHackathon;
import it.unicam.hackathon.interfaces.IModificaHackathon;
import it.unicam.hackathon.interfaces.IVisualizzaHackathon;
import it.unicam.hackathon.repository.HackathonRepository;
import it.unicam.hackathon.service.HackathonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Controller per la gestione degli hackathon.
 *
 * Implementa le interfacce boundary definite nei sequence diagram:
 *   - 3a iterazione: ICreazioneHackathon, IElencoHackathon, IIscrizioneHackathon
 *   - 4a iterazione: IModificaHackathon, IEliminazioneHackathon,
 *                    IVisualizzaHackathon, IAssegnaVincitore
 */
@Component
public class HackathonController implements
        ICreazioneHackathon,
        IElencoHackathon,
        IIscrizioneHackathon,
        IModificaHackathon,
        IEliminazioneHackathon,
        IVisualizzaHackathon,
        IAssegnaVincitore {

    private final HackathonService hackathonService;
    private final HackathonRepository hackathonRepository;
    private final HackathonBuilder hackathonBuilder;

    @Autowired
    public HackathonController(HackathonService hackathonService,
                               HackathonRepository hackathonRepository,
                               HackathonBuilder hackathonBuilder) {
        this.hackathonService = hackathonService;
        this.hackathonRepository = hackathonRepository;
        this.hackathonBuilder = hackathonBuilder;
    }

    /**
     * Crea un hackathon usando il Builder.
     */
    public Hackathon creaHackathon(String nome, String tema, Date inizio, Date fine,
                                   Organizzatore organizzatore) {
        Hackathon h = hackathonBuilder
                .nome(nome)
                .argomento(tema)
                .dataInizio(inizio)
                .dataFine(fine)
                .scadenzaIscrizioni(inizio)
                .organizzatore(organizzatore)
                .build();
        return hackathonService.elaboraCreazioneHackathon(h);
    }

    public void pubblicaHackathon(Integer idHackathon) {
        hackathonService.apriIscrizioni(idHackathon);
    }

    public void iscriviTeam(Integer idTeam, Integer idHackathon) {

        hackathonService.iscriviTeamAdHackathon(idTeam, idHackathon);
    }

    // ============================================================
    // === METODI PUBBLICI AGGIUNTI NELLA 4a ITERAZIONE         ===
    // ============================================================

    /**
     * Modifica un hackathon esistente.
     * Sequence diagram: "Modifica Hackathon".
     */
    public Hackathon modificaHackathon(Integer idHackathon, Hackathon dati) {
        dati.setId(idHackathon);
        return hackathonService.aggiornaHackathon(dati);
    }

    /**
     * Elimina un hackathon dal sistema.
     * Sequence diagram: "Eliminazione Hackathon".
     */
    public void eliminaHackathon(Integer idHackathon) {
        hackathonService.rimuoviHackathon(idHackathon);
    }

    /**
     * Restituisce tutti gli hackathon a cui un utente partecipa.
     * Sequence diagram: "Visualizza I Miei Hackathon".
     */
    public List<Hackathon> getHackathonUtente(Integer idUtente) {
        return hackathonService.ottieniHackathonUtente(idUtente);
    }

    /**
     * Restituisce la lista dei team iscritti a un hackathon.
     * Sequence diagram: "Assegna Vincitore".
     */
    public List<Team> getTeamPartecipanti(Integer idHackathon) {
        return hackathonService.ottieniTeamHackathon(idHackathon);
    }

    /**
     * Restituisce tutti gli hackathon gestiti da un organizzatore.
     * Sequence diagram: "Modifica Hackathon".
     */
    public List<Hackathon> getHackathonDisponibili(Integer idOrganizzatore) {
        return hackathonService.ottieniHackathonPerOrganizzatore(idOrganizzatore);
    }

    /**
     * Restituisce i dettagli completi di un hackathon dato l'id.
     * Sequence diagram: "Modifica Hackathon".
     */
    public Hackathon getDettagliHackathon(Integer idHackathon) {
        return hackathonService.ottieniDettagli(idHackathon);
    }

    // ============================================================
    // === Implementazioni interfacce boundary                  ===
    // ============================================================

    // === ICreazioneHackathon ===

    @Override
    public void mostraFormCreazione() { }

    @Override
    public void inserisciDatiHackathon(Hackathon dati) {
        hackathonService.elaboraCreazioneHackathon(dati);
    }

    @Override
    public void confermaCreazione() { }

    @Override
    public void mostraErroreDati() { }

    @Override
    public void notificaCreazioneCompletata() { }

    // === IElencoHackathon ===

    @Override
    public List<Hackathon> visualizzaElencoHackathon() {
        return hackathonRepository.findAll();
    }

    @Override
    public Hackathon visualizzaDettagliHackathon(Integer id) {
        return hackathonRepository.findById(id).orElse(null);
    }

    // === IIscrizioneHackathon ===

    @Override
    public void selezionaHackathon(Integer id) { }

    @Override
    public void confermaIscrizione(Integer idTeam, Integer idHackathon) {
        iscriviTeam(idTeam, idHackathon);
    }

    @Override
    public void mostraErroreIscrizione() { }

    @Override
    public void notificaIscrizioneCompletata() { }

    // === IModificaHackathon (4a iterazione) ===

    @Override
    public void mostraElencoHackathon(List<Hackathon> hackathons) {
        // boundary: in un'app reale aggiornerebbe la vista
    }

    @Override
    public void mostraModuloModifica(Hackathon h) {
        // boundary: in un'app reale aprirebbe la form di modifica
    }

    @Override
    public void notificaModifica() { }

    @Override
    public void mostraMessaggioErrore() { }

    // === IEliminazioneHackathon (4a iterazione) ===

    @Override
    public void richiedeModuloEliminazione() { }

    @Override
    public void mostraModuloEliminazione() { }

    @Override
    public void selezionaHackathonDaEliminare(Integer idHackathon) {
        eliminaHackathon(idHackathon);
    }

    @Override
    public void notificaEliminazione() { }

    @Override
    public void mostraMessaggioConferma() { }

    // === IVisualizzaHackathon (4a iterazione) ===

    @Override
    public void richiediVisualizzareIMieiHackathon() { }

    // mostraElencoHackathon e' definito in IModificaHackathon (stessa firma):
    // l'implementazione sopra copre entrambe le interfacce.

    @Override
    public void mostraErroreNessunHackathon() { }

    // === IAssegnaVincitore (4a iterazione) ===

    @Override
    public void mostraTeamPartecipanti(List<Team> teams) { }

    @Override
    public void assegnaVincitore(Integer idHackathon, Integer idTeam) {
        hackathonService.assegnaVincitore(idHackathon, idTeam);
    }

    @Override
    public void notificaAssegnazione() { }

    // mostraMessaggioConferma() e' gia' implementato in IEliminazioneHackathon:
    // la stessa implementazione vale anche qui (stessa firma).
}
