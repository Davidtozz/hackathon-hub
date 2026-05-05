package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.repository.InvitoRepository;
import it.unicam.hackathon.rest.dto.InvitoRequest;
import it.unicam.hackathon.service.InvitoService;
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
 * REST API per la gestione degli inviti ai team.
 *
 * Endpoint base: /api/inviti
 */
@RestController
@RequestMapping("/api/inviti")
public class InvitoRestController {

    private final InvitoService invitoService;
    private final InvitoRepository invitoRepository;

    @Autowired
    public InvitoRestController(InvitoService invitoService, InvitoRepository invitoRepository) {
        this.invitoService = invitoService;
        this.invitoRepository = invitoRepository;
    }

    /** POST /api/inviti — crea un nuovo invito. */
    @PostMapping
    public ResponseEntity<Invito> creaInvito(@RequestBody InvitoRequest req) {
        Invito i = invitoService.elaboraInvito(req.getNome(), req.getCognome(), req.getEmail());
        return ResponseEntity.ok(i);
    }

    /** GET /api/inviti — elenco di tutti gli inviti. */
    @GetMapping
    public ResponseEntity<List<Invito>> getAll() {
        return ResponseEntity.ok(invitoRepository.findAll());
    }

    /** GET /api/inviti/utente/{idUtente} — inviti ricevuti da un utente. */
    @GetMapping("/utente/{idUtente}")
    public ResponseEntity<List<Invito>> getInvitiByUtente(@PathVariable Integer idUtente) {
        return ResponseEntity.ok(invitoService.visualizzaInvitiByUtente(idUtente));
    }

    /**
     * PUT /api/inviti/{idInvito}/rispondi — accetta o rifiuta un invito.
     * Body: { "accetta": true/false }
     */
    @PutMapping("/{idInvito}/rispondi")
    public ResponseEntity<Void> rispondi(@PathVariable Integer idInvito,
                                         @RequestBody Map<String, Boolean> body) {
        Boolean accetta = body.get("accetta");
        invitoService.rispondiInvito(idInvito, accetta != null && accetta);
        return ResponseEntity.ok().build();
    }
}
