package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.Mentore;
import it.unicam.hackathon.actors.Segnalazione;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.enums.StatoSegnalazione;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.SegnalazioneRepository;
import it.unicam.hackathon.repository.TeamRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.SegnalazioneRequest;
import it.unicam.hackathon.service.SegnalazioneService;
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
 * REST API per la gestione delle segnalazioni da parte dei mentori.
 *
 * Endpoint base: /api/segnalazioni
 */
@RestController
@RequestMapping("/api/segnalazioni")
public class SegnalazioneRestController {

    private final SegnalazioneService segnalazioneService;
    private final SegnalazioneRepository segnalazioneRepository;
    private final UtenteRepository utenteRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public SegnalazioneRestController(SegnalazioneService segnalazioneService,
                                      SegnalazioneRepository segnalazioneRepository,
                                      UtenteRepository utenteRepository,
                                      TeamRepository teamRepository) {
        this.segnalazioneService = segnalazioneService;
        this.segnalazioneRepository = segnalazioneRepository;
        this.utenteRepository = utenteRepository;
        this.teamRepository = teamRepository;
    }

    /** POST /api/segnalazioni — un mentore crea una segnalazione su un team. */
    @PostMapping
    public ResponseEntity<Segnalazione> creaSegnalazione(@RequestBody SegnalazioneRequest req) {
        Mentore mentore = (Mentore) utenteRepository.findById(req.getIdMentore()).orElse(null);
        Team team = teamRepository.findById(req.getIdTeam()).orElse(null);
        if (mentore == null || team == null) return ResponseEntity.badRequest().build();
        Segnalazione s = segnalazioneService.creaSegnalazione(mentore, team, req.getMotivazione());
        s = segnalazioneService.salvaSegnalazione(s);
        return ResponseEntity.ok(s);
    }

    /** GET /api/segnalazioni — elenco di tutte le segnalazioni. */
    @GetMapping
    public ResponseEntity<List<Segnalazione>> getAll() {
        return ResponseEntity.ok(segnalazioneService.getSegnalazioni());
    }

    /** GET /api/segnalazioni/hackathon/{idHackathon} — segnalazioni di un hackathon. */
    @GetMapping("/hackathon/{idHackathon}")
    public ResponseEntity<List<Segnalazione>> getByHackathon(@PathVariable Integer idHackathon) {
        return ResponseEntity.ok(segnalazioneService.getSegnalazioniByHackathon(idHackathon));
    }

    /**
     * PUT /api/segnalazioni/{id}/gestisci — l'organizzatore aggiorna lo stato della segnalazione.
     * Use case: "Sanzione Team" (lo stato passa a RISOLTA = il team e' stato sanzionato).
     * Body: { "stato": "IN_REVISIONE" | "RISOLTA" | "RESPINTA" }
     */
    @PutMapping("/{id}/gestisci")
    public ResponseEntity<Segnalazione> gestisci(@PathVariable Integer id,
                                                 @RequestBody Map<String, String> body) {
        Segnalazione s = segnalazioneRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Segnalazione non trovata"));
        String stato = body.get("stato");
        if (stato == null || stato.isBlank()) {
            throw new HackathonException("Campo 'stato' obbligatorio");
        }
        s.gestisci(StatoSegnalazione.valueOf(stato.toUpperCase()));
        return ResponseEntity.ok(segnalazioneRepository.save(s));
    }
}
