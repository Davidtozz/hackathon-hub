package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository in-memory per i Team.
 */
@Repository
public class TeamRepository extends AbstractInMemoryRepository<Team> {

    public Optional<Team> findByNome(String nome) {
        if (nome == null) return Optional.empty();
        return storage.values().stream()
                .filter(t -> nome.equalsIgnoreCase(t.getNome()))
                .findFirst();
    }

    public boolean existsByNome(String nome) {
        return findByNome(nome).isPresent();
    }

    /**
     * Restituisce tutti i team gestiti da un certo TeamLeader.
     * Sequence diagram: "Eliminazione Team" -> findByLeader.
     */
    public List<Team> findByLeader(Integer idLeader) {
        if (idLeader == null) return List.of();
        return storage.values().stream()
                .filter(t -> t.getTeamLeader() != null
                        && idLeader.equals(t.getTeamLeader().getId()))
                .toList();
    }

    /**
     * Restituisce il team a cui appartiene un certo membro (se esiste).
     * Sequence diagram: "Lascia Gruppo" -> findByMembro.
     */
    public Optional<Team> findByMembro(Integer idMembro) {
        if (idMembro == null) return Optional.empty();
        return storage.values().stream()
                .filter(t -> t.getMembriDelTeam() != null
                        && t.getMembriDelTeam().stream()
                            .anyMatch(m -> idMembro.equals(m.getId())))
                .findFirst();
    }

    /**
     * Cancella un team dato il suo id. Alias semantico di deleteById().
     * Sequence diagram: "Eliminazione Team" -> cancellaPerId.
     */
    public void cancellaPerId(Integer id) {
        deleteById(id);
    }
}
