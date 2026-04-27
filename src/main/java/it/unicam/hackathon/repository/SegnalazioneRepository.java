package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Segnalazione;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per le segnalazioni.
 */
@Repository
public class SegnalazioneRepository extends AbstractInMemoryRepository<Segnalazione> {

    public List<Segnalazione> findByTeam(Integer idTeam) {
        return storage.values().stream()
                .filter(s -> s.getTeamSegnalato() != null
                        && s.getTeamSegnalato().getId() != null
                        && s.getTeamSegnalato().getId().equals(idTeam))
                .toList();
    }

    public List<Segnalazione> findByHackathon(Integer idHackathon) {
        return storage.values().stream()
                .filter(s -> s.getHackathon() != null
                        && s.getHackathon().getId() != null
                        && s.getHackathon().getId().equals(idHackathon))
                .toList();
    }
}
