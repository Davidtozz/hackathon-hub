package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.repository.TeamRepository;
import it.unicam.hackathon.rest.dto.SottomissioneRequest;
import it.unicam.hackathon.service.SottomissioneService;
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
 * REST API per la gestione delle sottomissioni dei progetti.
 *
 * Endpoint base: /api/sottomissioni
 */
@RestController
@RequestMapping("/api/sottomissioni")
public class SottomissioneRestController {

    private final SottomissioneService sottomissioneService;
    private final TeamRepository teamRepository;

    @Autowired
    public SottomissioneRestController(SottomissioneService sottomissioneService,
                                       TeamRepository teamRepository) {
        this.sottomissioneService = sottomissioneService;
        this.teamRepository = teamRepository;
    }

    /** POST /api/sottomissioni — crea una nuova sottomissione per un team. */
    @PostMapping
    public ResponseEntity<Sottomissione> creaSottomissione(@RequestBody SottomissioneRequest req) {
        Team t = teamRepository.findById(req.getIdTeam()).orElse(null);
        if (t == null) return ResponseEntity.badRequest().build();
        Sottomissione s = sottomissioneService.creaSottomissione(t, req.getLink());
        return ResponseEntity.ok(s);
    }

    /** PUT /api/sottomissioni/{id} — aggiorna il link di una sottomissione. */
    @PutMapping("/{id}")
    public ResponseEntity<Sottomissione> aggiornaSottomissione(@PathVariable Integer id,
                                                               @RequestBody Map<String, String> body) {
        String nuovoLink = body.get("link");
        return ResponseEntity.ok(sottomissioneService.aggiornaSottomissione(id, nuovoLink));
    }

    /** GET /api/sottomissioni/hackathon/{idHackathon} — sottomissioni di un hackathon. */
    @GetMapping("/hackathon/{idHackathon}")
    public ResponseEntity<List<Sottomissione>> getByHackathon(@PathVariable Integer idHackathon) {
        return ResponseEntity.ok(sottomissioneService.getSottomissioniByHackathon(idHackathon));
    }

    /** GET /api/sottomissioni/team/{idTeam} — sottomissioni di un team. */
    @GetMapping("/team/{idTeam}")
    public ResponseEntity<List<Sottomissione>> getByTeam(@PathVariable Integer idTeam) {
        return ResponseEntity.ok(sottomissioneService.getSottomissioniByTeam(idTeam));
    }
}
