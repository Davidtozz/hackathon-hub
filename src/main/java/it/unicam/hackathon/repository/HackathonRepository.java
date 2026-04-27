package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.enums.StatoHackathon;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per gli Hackathon.
 */
@Repository
public class HackathonRepository extends AbstractInMemoryRepository<Hackathon> {

    public List<Hackathon> findByStato(StatoHackathon stato) {
        return storage.values().stream()
                .filter(h -> stato == h.getStato())
                .toList();
    }
}
