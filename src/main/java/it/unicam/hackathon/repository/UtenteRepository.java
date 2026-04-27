package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.BaseUtente;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository in-memory per gli utenti (generico su BaseUtente).
 */
@Repository
public class UtenteRepository extends AbstractInMemoryRepository<BaseUtente> {

    public Optional<BaseUtente> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return storage.values().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}
