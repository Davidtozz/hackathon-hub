package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.controllers.TeamController;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.TeamRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.TeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * REST API per la gestione dei team.
 * Espone via HTTP tutti gli use case relativi ai team.
 *
 * Endpoint base: /api/team
 */
@RestController
@RequestMapping("/api/team")
public class TeamRestController {

    private final TeamController teamController;
    private final TeamRepository teamRepository;
    private final UtenteRepository utenteRepository;

    @Autowired
    public TeamRestController(TeamController teamController,
                              TeamRepository teamRepository,
                              UtenteRepository utenteRepository) {
        this.teamController = teamController;
        this.teamRepository = teamRepository;
        this.utenteRepository = utenteRepository;
    }

    /** POST /api/team — crea un nuovo team. L'utente con id idLeader diventa TeamLeader. */
    @PostMapping
    public ResponseEntity<Team> creaTeam(@RequestBody TeamRequest req) {
        BaseUtente u = utenteRepository.findById(req.getIdLeader()).orElse(null);
        if (!(u instanceof UtenteSenzaTeam)) {
            return ResponseEntity.badRequest().build();
        }
        Team t = teamController.creaTeam(req.getNome(), (UtenteSenzaTeam) u);
        return ResponseEntity.ok(t);
    }

    /** GET /api/team — elenco di tutti i team (use case "Visualizza Team"). */
    @GetMapping
    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    /** GET /api/team/{id} — dettagli di un team (use case "Visualizza Team"). */
    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(@PathVariable Integer id) {
        Team t = teamRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Team non trovato: " + id));
        return ResponseEntity.ok(t);
    }

    /** GET /api/team/{id}/membri — membri di un team. */
    @GetMapping("/{id}/membri")
    public ResponseEntity<List<MembroDelTeam>> getMembri(@PathVariable Integer id) {
        Team t = teamRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Team non trovato: " + id));
        return ResponseEntity.ok(t.getMembriDelTeam());
    }

    /** GET /api/team/leader/{idLeader} — team gestiti da un certo TeamLeader (4a iter). */
    @GetMapping("/leader/{idLeader}")
    public ResponseEntity<List<Team>> getTeamsByLeader(@PathVariable Integer idLeader) {
        return ResponseEntity.ok(teamController.getTeamsByLeader(idLeader));
    }

    /** DELETE /api/team/{idTeam} — elimina un team (4a iter). */
    @DeleteMapping("/{idTeam}")
    public ResponseEntity<Void> eliminaTeam(@PathVariable Integer idTeam) {
        teamController.eliminaTeam(idTeam);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/team/{idTeam}/abbandona — il membro con idMembro abbandona il team
     * (use case "Lascia Gruppo" della 4a iter).
     * Body: { "idMembro": <id> }
     */
    @PostMapping("/{idTeam}/abbandona")
    public ResponseEntity<Void> abbandonaGruppo(@PathVariable Integer idTeam,
                                                @RequestBody Map<String, Integer> body) {
        Integer idMembro = body.get("idMembro");
        teamController.abbandonaGruppo(idMembro);
        return ResponseEntity.noContent().build();
    }
}
