package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.enums.EnumStatoInvito;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository in-memory per gli inviti.
 */
@Repository
public class InvitoRepository extends AbstractInMemoryRepository<Invito> {

    /**
     * Trova tutti gli inviti inviati a un utente specifico.
     */
    public List<Invito> findByDestinatario(Integer idUtente) {
        return storage.values().stream()
                .filter(i -> i.getDestinatario() != null
                        && i.getDestinatario().getId() != null
                        && i.getDestinatario().getId().equals(idUtente))
                .toList();
    }

    /**
     * Aggiorna lo stato di un invito.
     */
    public void updateStato(Integer id, EnumStatoInvito stato) {
        findById(id).ifPresent(i -> i.setStato(stato));
    }
}
