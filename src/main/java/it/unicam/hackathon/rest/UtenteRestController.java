package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.actors.Giudice;
import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Mentore;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.LoginRequest;
import it.unicam.hackathon.rest.dto.UtenteRequest;
import it.unicam.hackathon.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * REST API per la gestione degli utenti: registrazione, login, profilo.
 *
 * Endpoint base: /api/utenti
 */
@RestController
@RequestMapping("/api/utenti")
public class UtenteRestController {

    private final UtenteService utenteService;
    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteRestController(UtenteService utenteService, UtenteRepository utenteRepository) {
        this.utenteService = utenteService;
        this.utenteRepository = utenteRepository;
    }

    /** POST /api/utenti/registra — registra un nuovo utente. */
    @PostMapping("/registra")
    public ResponseEntity<BaseUtente> registra(@RequestBody UtenteRequest req) {
        BaseUtente u = creaUtenteDaRuolo(req);
        utenteService.registraNuovoUtente(u);
        return ResponseEntity.ok(u);
    }

    /** POST /api/utenti/login — autentica un utente con email + password. */
    @PostMapping("/login")
    public ResponseEntity<BaseUtente> login(@RequestBody LoginRequest req) {
        BaseUtente u = utenteService.autenticaUtente(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(u);
    }

    /** GET /api/utenti/{id} — restituisce il profilo di un utente. */
    @GetMapping("/{id}")
    public ResponseEntity<BaseUtente> getProfilo(@PathVariable Integer id) {
        return ResponseEntity.ok(utenteService.getProfilo(id));
    }

    /** GET /api/utenti — elenco di tutti gli utenti registrati. */
    @GetMapping
    public ResponseEntity<List<BaseUtente>> getAll() {
        return ResponseEntity.ok(utenteRepository.findAll());
    }

    // ========================================================
    // Helpers
    // ========================================================

    /**
     * Crea l'istanza concreta corretta in base al ruolo richiesto.
     * Default: UtenteSenzaTeam.
     */
    private BaseUtente creaUtenteDaRuolo(UtenteRequest req) {
        String ruolo = req.getRuolo() != null ? req.getRuolo().toUpperCase() : "UTENTE_SENZA_TEAM";
        BaseUtente u = switch (ruolo) {
            case "MEMBRO_DEL_TEAM" -> new MembroDelTeam();
            case "ORGANIZZATORE" -> new Organizzatore();
            case "GIUDICE" -> new Giudice();
            case "MENTORE" -> new Mentore();
            default -> new UtenteSenzaTeam();
        };
        u.setNome(req.getNome());
        u.setCognome(req.getCognome());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setCellulare(req.getCellulare());
        u.setDataIscrizione(req.getDataIscrizione() != null ? req.getDataIscrizione() : new Date());
        return u;
    }
}
