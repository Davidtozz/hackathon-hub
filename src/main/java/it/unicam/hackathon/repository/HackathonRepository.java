package it.unicam.hackathon.repository;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.enums.StatoHackathon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    /**
     * Restituisce tutti gli hackathon creati da un certo organizzatore.
     * Sequence diagram: "Modifica Hackathon" -> findByOrganizzatore.
     */
    public List<Hackathon> findByOrganizzatore(Integer idOrganizzatore) {
        if (idOrganizzatore == null) return new ArrayList<>();
        return storage.values().stream()
                .filter(h -> h.getOrganizzatore() != null
                        && idOrganizzatore.equals(h.getOrganizzatore().getId()))
                .toList();
    }

    /**
     * Aggiorna un hackathon esistente. Alias semantico di save().
     * Sequence diagram: "Modifica Hackathon" -> update.
     */
    public Hackathon update(Hackathon h) {
        return save(h);
    }

    /**
     * Cancella un hackathon dato il suo id. Alias semantico di deleteById().
     * Sequence diagram: "Eliminazione Hackathon" -> cancellaPerId.
     */
    public void cancellaPerId(Integer id) {
        deleteById(id);
    }

    /**
     * Restituisce tutti gli hackathon a cui un utente partecipa, in qualunque ruolo:
     * organizzatore, giudice, mentore, team leader o membro di un team iscritto.
     * Sequence diagram: "Visualizza I Miei Hackathon" -> trovaPerUtenteId.
     */
    public List<Hackathon> trovaPerUtenteId(Integer idUtente) {
        if (idUtente == null) return new ArrayList<>();
        return storage.values().stream()
                .filter(h -> coinvolgeUtente(h, idUtente))
                .toList();
    }

    /**
     * True se l'utente con l'id indicato e' coinvolto a qualunque titolo
     * nell'hackathon.
     */
    private boolean coinvolgeUtente(Hackathon h, Integer idUtente) {
        if (h.getOrganizzatore() != null && idUtente.equals(h.getOrganizzatore().getId())) return true;
        if (h.getGiudice() != null && idUtente.equals(h.getGiudice().getId())) return true;
        if (h.getMentori() != null && h.getMentori().stream()
                .anyMatch(m -> idUtente.equals(m.getId()))) return true;
        if (h.getTeams() == null) return false;
        for (Team t : h.getTeams()) {
            if (t.getTeamLeader() != null && idUtente.equals(t.getTeamLeader().getId())) return true;
            if (t.getMembriDelTeam() != null) {
                for (MembroDelTeam m : t.getMembriDelTeam()) {
                    if (idUtente.equals(m.getId())) return true;
                }
            }
        }
        return false;
    }
}
