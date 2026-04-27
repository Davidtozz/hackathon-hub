package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per i ticket di supporto.
 */
@Repository
public class TicketRepository extends AbstractInMemoryRepository<Ticket> {

    public List<Ticket> findByMentore(Integer idMentore) {
        return storage.values().stream()
                .filter(t -> t.getMentoreAssegnato() != null
                        && t.getMentoreAssegnato().getId() != null
                        && t.getMentoreAssegnato().getId().equals(idMentore))
                .toList();
    }
}
