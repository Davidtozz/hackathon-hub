package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Mentore;
import it.unicam.hackathon.actors.Ticket;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.TicketRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST API per i ticket di supporto.
 *
 * Use case coperti:
 *   - "Proporre aiuto": un membro del team apre un ticket verso un mentore
 *   - "Supportare Team": il mentore risponde al ticket
 *
 * Endpoint base: /api/ticket
 */
@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {

    private final TicketRepository ticketRepository;
    private final UtenteRepository utenteRepository;

    @Autowired
    public TicketRestController(TicketRepository ticketRepository, UtenteRepository utenteRepository) {
        this.ticketRepository = ticketRepository;
        this.utenteRepository = utenteRepository;
    }

    /**
     * POST /api/ticket — un membro del team apre un ticket di supporto verso un mentore.
     * Use case: "Proporre aiuto".
     */
    @PostMapping
    public ResponseEntity<Ticket> apriTicket(@RequestBody TicketRequest req) {
        MembroDelTeam autore = (MembroDelTeam) utenteRepository.findById(req.getIdAutore())
                .orElseThrow(() -> new HackathonException("Autore non trovato"));
        Mentore mentore = (Mentore) utenteRepository.findById(req.getIdMentore())
                .orElseThrow(() -> new HackathonException("Mentore non trovato"));

        Ticket t = new Ticket();
        t.setDescrizione(req.getDescrizione());
        t.setAutore(autore);
        t.setMentoreAssegnato(mentore);
        return ResponseEntity.ok(ticketRepository.save(t));
    }

    /**
     * PUT /api/ticket/{id}/risolvi — il mentore risolve il ticket con una risposta.
     * Use case: "Supportare Team".
     * Body: { "risposta": "..." }
     */
    @PutMapping("/{id}/risolvi")
    public ResponseEntity<Ticket> risolvi(@PathVariable Integer id,
                                          @RequestBody Map<String, String> body) {
        Ticket t = ticketRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Ticket non trovato"));
        t.risolvi(body.get("risposta"));
        return ResponseEntity.ok(ticketRepository.save(t));
    }

    /** GET /api/ticket/mentore/{idMentore} — ticket assegnati a un mentore. */
    @GetMapping("/mentore/{idMentore}")
    public ResponseEntity<List<Ticket>> getByMentore(@PathVariable Integer idMentore) {
        return ResponseEntity.ok(ticketRepository.findByMentore(idMentore));
    }

    /** GET /api/ticket — elenco di tutti i ticket. */
    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }
}
