package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Sottomissione;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per le sottomissioni.
 */
@Repository
public class SottomissioneRepository extends AbstractInMemoryRepository<Sottomissione> {

    public List<Sottomissione> findByTeam(Integer idTeam) {
        return storage.values().stream()
                .filter(s -> s.getTeam() != null
                        && s.getTeam().getId() != null
                        && s.getTeam().getId().equals(idTeam))
                .toList();
    }

    public List<Sottomissione> findByHackathon(Integer idHackathon) {
        return storage.values().stream()
                .filter(s -> s.getHackathon() != null
                        && s.getHackathon().getId() != null
                        && s.getHackathon().getId().equals(idHackathon))
                .toList();
    }
}
