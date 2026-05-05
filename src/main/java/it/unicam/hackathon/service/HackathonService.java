package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.enums.StatoHackathon;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.HackathonRepository;
import it.unicam.hackathon.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione degli hackathon: creazione, iscrizioni, chiusura, assegnazione vincitore.
 * Nella 4a iterazione vengono aggiunti i metodi di modifica, eliminazione e visualizzazione
 * "personalizzata" per utente.
 */
@Service
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public HackathonService(HackathonRepository hackathonRepository, TeamRepository teamRepository) {
        this.hackathonRepository = hackathonRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Verifica che i campi minimi dell'hackathon siano valorizzati.
     */
    public boolean controllaDati(Hackathon dati) {
        if (dati == null) return false;
        return dati.getNome() != null && !dati.getNome().isBlank()
                && dati.getDataInizio() != null
                && dati.getDataFine() != null
                && dati.getDataInizio().before(dati.getDataFine());
    }

    /**
     * Elabora e salva un nuovo hackathon.
     */
    public Hackathon elaboraCreazioneHackathon(Hackathon dati) {
        if (!controllaDati(dati)) {
            throw new HackathonException("Dati hackathon non validi");
        }
        if (dati.getStato() == null) {
            dati.setStato(StatoHackathon.ISCRIZIONI_APERTE);
        }
        return hackathonRepository.save(dati);
    }

    /**
     * Verifica se un team puo' iscriversi a un hackathon.
     */
    public boolean verificaIscrizione(Integer idTeam, Integer idHackathon) {
        Hackathon h = hackathonRepository.findById(idHackathon).orElse(null);
        if (h == null) return false;
        if (h.getStato() != StatoHackathon.ISCRIZIONI_APERTE) return false;
        if (h.getTeams().stream().anyMatch(t -> t.getId() != null && t.getId().equals(idTeam))) return false;
        return teamRepository.existsById(idTeam);
    }

    /**
     * Iscrive un team a un hackathon.
     */
    public void iscriviTeamAdHackathon(Team team, Hackathon hackathon) {
        if (team == null || hackathon == null) {
            throw new HackathonException("Team o Hackathon nulli");
        }
        if (!verificaIscrizione(team.getId(), hackathon.getId())) {
            throw new HackathonException("Iscrizione non consentita");
        }
        hackathon.getTeams().add(team);
        hackathonRepository.save(hackathon);
    }

    /**
     * Iscrive un team a un hackathon a partire dagli id.
     * Carica i veri oggetti dal repository (così Team e Hackathon mantengono tutti i campi).
     */
    public void iscriviTeamAdHackathon(Integer idTeam, Integer idHackathon) {
        if (idTeam == null || idHackathon == null) {
            throw new HackathonException("Id Team o Hackathon nulli");
        }
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HackathonException("Team non trovato con id " + idTeam));
        Hackathon hackathon = hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato con id " + idHackathon));
        if (!verificaIscrizione(idTeam, idHackathon)) {
            throw new HackathonException("Iscrizione non consentita "
                    + "(hackathon non in iscrizioni aperte, oppure team già iscritto)");
        }
        hackathon.getTeams().add(team);
        hackathonRepository.save(hackathon);
    }

    /**
     * Apre le iscrizioni a un hackathon.
     */
    public void apriIscrizioni(Integer idHackathon) {
        Hackathon h = hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato: " + idHackathon));
        h.setStato(StatoHackathon.ISCRIZIONI_APERTE);
        hackathonRepository.save(h);
    }

    /**
     * Chiude un hackathon (passa a stato CONCLUSO).
     */
    public void chiudiHackathon(Integer idHackathon) {
        Hackathon h = hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato: " + idHackathon));
        h.setStato(StatoHackathon.CONCLUSO);
        hackathonRepository.save(h);
    }

    /**
     * Assegna un team come vincitore dell'hackathon.
     * Sequence diagram: "Assegna Vincitore".
     */
    public void assegnaVincitore(Integer idHackathon, Integer idTeam) {
        Hackathon h = hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato: " + idHackathon));
        Team t = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HackathonException("Team non trovato: " + idTeam));
        if (!h.getTeams().contains(t)) {
            throw new HackathonException("Il team non e' iscritto a questo hackathon");
        }
        h.setVincitore(t);
        hackathonRepository.save(h);
    }

    // ============================================================
    // === METODI AGGIUNTI NELLA 4a ITERAZIONE                  ===
    // ============================================================

    /**
     * Restituisce tutti gli hackathon creati da un certo organizzatore.
     * Sequence diagram: "Modifica Hackathon".
     */
    public List<Hackathon> ottieniHackathonPerOrganizzatore(Integer idOrganizzatore) {
        if (idOrganizzatore == null) {
            throw new HackathonException("Id organizzatore obbligatorio");
        }
        return hackathonRepository.findByOrganizzatore(idOrganizzatore);
    }

    /**
     * Restituisce i dati completi di un hackathon dato il suo id.
     * Sequence diagram: "Modifica Hackathon".
     */
    public Hackathon ottieniDettagli(Integer idHackathon) {
        return hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato: " + idHackathon));
    }

    /**
     * Verifica che i dati aggiornati di un hackathon siano validi.
     * Sequence diagram: "Modifica Hackathon" -> alt[dati non validi].
     */
    public boolean verificaDatiInseriti(Hackathon datiAggiornati) {
        return controllaDati(datiAggiornati);
    }

    /**
     * Aggiorna un hackathon esistente con i nuovi dati.
     * Sequence diagram: "Modifica Hackathon".
     */
    public Hackathon aggiornaHackathon(Hackathon datiAggiornati) {
        if (!verificaDatiInseriti(datiAggiornati)) {
            throw new HackathonException("Dati hackathon non validi");
        }
        if (datiAggiornati.getId() == null
                || !hackathonRepository.existsById(datiAggiornati.getId())) {
            throw new HackathonException("Hackathon da aggiornare non trovato");
        }
        return hackathonRepository.update(datiAggiornati);
    }

    /**
     * Rimuove un hackathon dal sistema.
     * Sequence diagram: "Eliminazione Hackathon".
     */
    public void rimuoviHackathon(Integer idHackathon) {
        if (idHackathon == null || !hackathonRepository.existsById(idHackathon)) {
            throw new HackathonException("Hackathon non trovato: " + idHackathon);
        }
        hackathonRepository.cancellaPerId(idHackathon);
    }

    /**
     * Restituisce tutti gli hackathon a cui un utente partecipa,
     * in qualunque ruolo (organizzatore, giudice, mentore, leader, membro).
     * Sequence diagram: "Visualizza I Miei Hackathon".
     */
    public List<Hackathon> ottieniHackathonUtente(Integer idUtente) {
        if (idUtente == null) {
            throw new HackathonException("Id utente obbligatorio");
        }
        return hackathonRepository.trovaPerUtenteId(idUtente);
    }

    /**
     * Restituisce la lista dei team partecipanti a un hackathon.
     * Sequence diagram: "Assegna Vincitore".
     */
    public List<Team> ottieniTeamHackathon(Integer idHackathon) {
        Hackathon h = ottieniDettagli(idHackathon);
        return h.getTeams();
    }
}
