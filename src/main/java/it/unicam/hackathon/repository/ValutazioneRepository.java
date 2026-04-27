package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Valutazione;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per le valutazioni.
 */
@Repository
public class ValutazioneRepository extends AbstractInMemoryRepository<Valutazione> {

    public List<Valutazione> findBySottomissione(Integer idSottomissione) {
        return storage.values().stream()
                .filter(v -> v.getSottomissione() != null
                        && v.getSottomissione().getId() != null
                        && v.getSottomissione().getId().equals(idSottomissione))
                .toList();
    }

    public List<Valutazione> findByGiudice(Integer idGiudice) {
        return storage.values().stream()
                .filter(v -> v.getGiudice() != null
                        && v.getGiudice().getId() != null
                        && v.getGiudice().getId().equals(idGiudice))
                .toList();
    }
}
