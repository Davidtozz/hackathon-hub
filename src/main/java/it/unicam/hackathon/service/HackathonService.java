package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.enums.StatoHackathon;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.HackathonRepository;
import it.unicam.hackathon.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione degli hackathon: creazione, iscrizioni, chiusura, assegnazione vincitore.
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
}
