package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.Giudice;
import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Mentore;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.HackathonRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.NominaStaffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API per la nomina di Giudici e Mentori in un Hackathon.
 *
 * Use case coperti: "Nomina Giudice", "Nomina Mentore", "Elimina Mentore".
 *
 * Endpoint base: /api/hackathon-staff
 */
@RestController
@RequestMapping("/api/hackathon-staff")
public class HackathonStaffRestController {

    private final HackathonRepository hackathonRepository;
    private final UtenteRepository utenteRepository;

    @Autowired
    public HackathonStaffRestController(HackathonRepository hackathonRepository,
                                        UtenteRepository utenteRepository) {
        this.hackathonRepository = hackathonRepository;
        this.utenteRepository = utenteRepository;
    }

    /** POST /api/hackathon-staff/giudice — nomina un utente come Giudice di un hackathon. */
    @PostMapping("/giudice")
    public ResponseEntity<Hackathon> nominaGiudice(@RequestBody NominaStaffRequest req) {
        Hackathon h = hackathonRepository.findById(req.getIdHackathon())
                .orElseThrow(() -> new HackathonException("Hackathon non trovato"));
        Giudice g = utenteRepository.findById(req.getIdUtente())
                .filter(u -> u instanceof Giudice)
                .map(u -> (Giudice) u)
                .orElseThrow(() -> new HackathonException("Utente non e' un Giudice"));
        h.setGiudice(g);
        return ResponseEntity.ok(hackathonRepository.save(h));
    }

    /** POST /api/hackathon-staff/mentore — aggiunge un Mentore allo staff. */
    @PostMapping("/mentore")
    public ResponseEntity<Hackathon> nominaMentore(@RequestBody NominaStaffRequest req) {
        Hackathon h = hackathonRepository.findById(req.getIdHackathon())
                .orElseThrow(() -> new HackathonException("Hackathon non trovato"));
        Mentore m = utenteRepository.findById(req.getIdUtente())
                .filter(u -> u instanceof Mentore)
                .map(u -> (Mentore) u)
                .orElseThrow(() -> new HackathonException("Utente non e' un Mentore"));
        if (!h.getMentori().contains(m)) {
            h.getMentori().add(m);
        }
        return ResponseEntity.ok(hackathonRepository.save(h));
    }

    /** DELETE /api/hackathon-staff/{idHackathon}/mentore/{idMentore} — rimuove un mentore. */
    @DeleteMapping("/{idHackathon}/mentore/{idMentore}")
    public ResponseEntity<Hackathon> eliminaMentore(@PathVariable Integer idHackathon,
                                                    @PathVariable Integer idMentore) {
        Hackathon h = hackathonRepository.findById(idHackathon)
                .orElseThrow(() -> new HackathonException("Hackathon non trovato"));
        h.getMentori().removeIf(m -> idMentore.equals(m.getId()));
        return ResponseEntity.ok(hackathonRepository.save(h));
    }
}
