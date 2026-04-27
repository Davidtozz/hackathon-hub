package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Team;
import org.springframework.stereotype.Repository;

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
}
