package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Mentore;
import it.unicam.hackathon.actors.Segnalazione;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.enums.StatoSegnalazione;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione delle segnalazioni create dai mentori verso i team.
 */
@Service
public class SegnalazioneService {

    private final SegnalazioneRepository segnalazioneRepository;

    @Autowired
    public SegnalazioneService(SegnalazioneRepository segnalazioneRepository) {
        this.segnalazioneRepository = segnalazioneRepository;
    }

    /**
     * Verifica che i dati minimi della segnalazione siano presenti.
     */
    public boolean verificaDati(Integer idTeam, String motivazione) {
        return idTeam != null && motivazione != null && !motivazione.isBlank();
    }

    /**
     * Crea una nuova segnalazione partendo dai dati di un mentore e un team.
     */
    public Segnalazione creaSegnalazione(Mentore mentore, Team team, String motivazione) {
        if (mentore == null || team == null) {
            throw new HackathonException("Mentore o team nulli");
        }
        if (!verificaDati(team.getId(), motivazione)) {
            throw new HackathonException("Dati segnalazione non validi");
        }
        Segnalazione s = new Segnalazione();
        s.setAutoreSegnalazione(mentore);
        s.setTeamSegnalato(team);
        s.setDescrizione(motivazione);
        s.setStatoSegnalazione(StatoSegnalazione.APERTA);
        return salvaSegnalazione(s);
    }

    /**
     * Salva una segnalazione nel repository.
     */
    public Segnalazione salvaSegnalazione(Segnalazione s) {
        return segnalazioneRepository.save(s);
    }

    /**
     * Notifica l'organizzatore della nuova segnalazione (placeholder).
     */
    public void notificaOrganizzatore(Segnalazione s) {
        // placeholder: invio email/notifica all'organizzatore
    }

    /**
     * Restituisce tutte le segnalazioni.
     */
    public List<Segnalazione> getSegnalazioni() {
        return segnalazioneRepository.findAll();
    }

    /**
     * Restituisce tutte le segnalazioni relative a un hackathon.
     */
    public List<Segnalazione> getSegnalazioniByHackathon(Integer idHackathon) {
        return segnalazioneRepository.findByHackathon(idHackathon);
    }
}
