package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.controllers.HackathonController;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.AssegnaVincitoreRequest;
import it.unicam.hackathon.rest.dto.HackathonRequest;
import it.unicam.hackathon.service.HackathonService;
import it.unicam.hackathon.strategy.StrategiaPagamento;
import it.unicam.hackathon.strategy.StrategiaPagamentoBonifico;
import it.unicam.hackathon.strategy.StrategiaPagamentoPayPal;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API per la gestione degli hackathon.
 * Espone via HTTP tutti gli use case relativi agli hackathon.
 *
 * Endpoint base: /api/hackathon
 */
@RestController
@RequestMapping("/api/hackathon")
public class HackathonRestController {

    private final HackathonController hackathonController;
    private final HackathonService hackathonService;
    private final UtenteRepository utenteRepository;

    @Autowired
    public HackathonRestController(HackathonController hackathonController,
                                   HackathonService hackathonService,
                                   UtenteRepository utenteRepository) {
        this.hackathonController = hackathonController;
        this.hackathonService = hackathonService;
        this.utenteRepository = utenteRepository;
    }

    // ========================================================
    // 3a iterazione
    // ========================================================

    /** POST /api/hackathon — crea un nuovo hackathon. */
    /** POST /api/hackathon — crea un nuovo hackathon. */
    @PostMapping
    public ResponseEntity<Hackathon> creaHackathon(@RequestBody HackathonRequest req) {
        if (req == null) {
            throw new HackathonException("Body della richiesta mancante o non in formato JSON.");
        }
        if (req.getNome() == null || req.getNome().isBlank()) {
            throw new HackathonException("Il campo 'nome' e' obbligatorio.");
        }
        if (req.getDataInizio() == null) {
            throw new HackathonException("Il campo 'dataInizio' e' obbligatorio (formato: yyyy-MM-dd'T'HH:mm:ss.SSSXXX).");
        }
        if (req.getDataFine() == null) {
            throw new HackathonException("Il campo 'dataFine' e' obbligatorio.");
        }
        if (req.getIdOrganizzatore() == null) {
            throw new HackathonException("Il campo 'idOrganizzatore' e' obbligatorio.");
        }

        // === RECUPERA ORGANIZZATORE ===
        BaseUtente u = utenteRepository.findById(req.getIdOrganizzatore())
                .orElseThrow(() -> new HackathonException(
                        "Utente non trovato con id " + req.getIdOrganizzatore()));
        if (!(u instanceof Organizzatore)) {
            throw new HackathonException(
                    "L'utente con id " + req.getIdOrganizzatore()
                            + " non e' un Organizzatore (e' un " + u.getClass().getSimpleName()
                            + "). Registra un utente con ruolo ORGANIZZATORE e usa quel suo id.");
        }
        Organizzatore org = (Organizzatore) u;

        // === COSTRUISCE HACKATHON USANDO TUTTI I CAMPI DEL DTO ===
        Hackathon h = new Hackathon();
        h.setNome(req.getNome());
        h.setArgomento(req.getArgomento());
        h.setRegolamento(req.getRegolamento());
        h.setLuogo(req.getLuogo());
        h.setPremio(req.getPremio());
        h.setDimensioneMaxTeam(req.getDimensioneMaxTeam());
        h.setDimensioneMinTeam(req.getDimensioneMinTeam());
        h.setNumMaxPersone(req.getNumMaxPersone());
        h.setNumMinPersone(req.getNumMinPersone());
        h.setDataInizio(req.getDataInizio());
        h.setDataFine(req.getDataFine());
        h.setScadenzaIscrizioni(req.getScadenzaIscrizioni() != null
                ? req.getScadenzaIscrizioni() : req.getDataInizio());
        h.setOrganizzatore(org);

        Hackathon salvato = hackathonService.elaboraCreazioneHackathon(h);

        System.out.println("[HackathonRestController] Hackathon creato: id=" + salvato.getId()
                + ", nome=" + salvato.getNome()
                + ", argomento=" + salvato.getArgomento()
                + ", organizzatoreId=" + (salvato.getOrganizzatore() == null ? "null" : salvato.getOrganizzatore().getId()));

        return ResponseEntity.ok(salvato);
    }

    /** GET /api/hackathon — elenco di tutti gli hackathon. */
    @GetMapping
    public ResponseEntity<List<Hackathon>> visualizzaElencoHackathon() {
        return ResponseEntity.ok(hackathonController.visualizzaElencoHackathon());
    }

    /** GET /api/hackathon/{id} — dettagli di un hackathon. */
    @GetMapping("/{id}")
    public ResponseEntity<Hackathon> visualizzaDettagli(@PathVariable Integer id) {
        Hackathon h = hackathonController.visualizzaDettagliHackathon(id);
        if (h == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(h);
    }

    /** PUT /api/hackathon/{id}/pubblica — apre le iscrizioni. */
    @PutMapping("/{id}/pubblica")
    public ResponseEntity<Void> pubblicaHackathon(@PathVariable Integer id) {
        hackathonController.pubblicaHackathon(id);
        return ResponseEntity.ok().build();
    }

    /** POST /api/hackathon/{id}/iscrivi — iscrive un team a un hackathon. */
    @PostMapping("/{id}/iscrivi")
    public ResponseEntity<?> iscriviTeam(@PathVariable Integer id,
                                         @RequestBody Map<String, Integer> body) {
        if (body == null || body.get("idTeam") == null) {
            return badRequest("Campo obbligatorio mancante", List.of("idTeam"), null);
        }
        Integer idTeam = body.get("idTeam");
        try {
            hackathonController.iscriviTeam(idTeam, id);
            return ResponseEntity.ok().build();
        } catch (HackathonException ex) {
            return badRequest(ex.getMessage(), null, Map.of("idTeam", idTeam, "idHackathon", id));
        }
    }

    // ========================================================
    // 4a iterazione
    // ========================================================

    /** PUT /api/hackathon/{id} — modifica un hackathon esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> modificaHackathon(@PathVariable Integer id,
                                               @RequestBody HackathonRequest req) {
        Organizzatore org = null;
        if (req != null && req.getIdOrganizzatore() != null) {
            BaseUtente u = utenteRepository.findById(req.getIdOrganizzatore()).orElse(null);
            if (!(u instanceof Organizzatore)) {
                return invalidOrganizzatore(req.getIdOrganizzatore(), u);
            }
            org = (Organizzatore) u;
        }
        try {
            Hackathon dati = mapRequestToHackathon(req, org);
            Hackathon aggiornato = hackathonController.modificaHackathon(id, dati);
            return ResponseEntity.ok(aggiornato);
        } catch (HackathonException ex) {
            return badRequest(ex.getMessage(), null, null);
        }
    }

    /** DELETE /api/hackathon/{id} — elimina un hackathon. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaHackathon(@PathVariable Integer id) {
        hackathonController.eliminaHackathon(id);
        return ResponseEntity.noContent().build();
    }

    /** GET /api/hackathon/utente/{idUtente} — hackathon a cui un utente partecipa. */
    @GetMapping("/utente/{idUtente}")
    public ResponseEntity<List<Hackathon>> getHackathonUtente(@PathVariable Integer idUtente) {
        return ResponseEntity.ok(hackathonController.getHackathonUtente(idUtente));
    }

    /** GET /api/hackathon/{id}/teams — team partecipanti a un hackathon. */
    @GetMapping("/{id}/teams")
    public ResponseEntity<List<Team>> getTeamPartecipanti(@PathVariable Integer id) {
        return ResponseEntity.ok(hackathonController.getTeamPartecipanti(id));
    }

    /** GET /api/hackathon/organizzatore/{idOrganizzatore} — hackathon di un organizzatore. */
    @GetMapping("/organizzatore/{idOrganizzatore}")
    public ResponseEntity<List<Hackathon>> getHackathonDisponibili(@PathVariable Integer idOrganizzatore) {
        return ResponseEntity.ok(hackathonController.getHackathonDisponibili(idOrganizzatore));
    }

    /** PUT /api/hackathon/{id}/vincitore — assegna il vincitore + paga il premio. */
    @PutMapping("/{id}/vincitore")
    public ResponseEntity<Map<String, Object>> assegnaVincitore(@PathVariable Integer id,
                                                                @RequestBody AssegnaVincitoreRequest req) {
        // 1. Assegnazione vincitore
        hackathonController.assegnaVincitore(id, req.getIdTeam());

        // 2. Pagamento premio (se richiesto)
        boolean pagamentoOk = false;
        if (req.getImportoPremio() != null && req.getMetodoPagamento() != null) {
            StrategiaPagamento metodo = creaStrategia(req);
            pagamentoOk = metodo.pagaPremio(req.getImportoPremio(), req.getIntestatario());
        }

        return ResponseEntity.ok(Map.of(
                "vincitoreAssegnato", true,
                "pagamentoEseguito", pagamentoOk
        ));
    }

    /** PUT /api/hackathon/{id}/chiudi — chiude un hackathon. */
    @PutMapping("/{id}/chiudi")
    public ResponseEntity<Void> chiudiHackathon(@PathVariable Integer id) {
        hackathonService.chiudiHackathon(id);
        return ResponseEntity.ok().build();
    }

    // ========================================================
    // Helpers privati
    // ========================================================

    private Hackathon mapRequestToHackathon(HackathonRequest req, Organizzatore org) {
        Hackathon h = new Hackathon();
        if (req == null) {
            return h;
        }
        h.setId(req.getId());
        h.setNome(req.getNome());
        h.setRegolamento(req.getRegolamento());
        h.setArgomento(req.getArgomento());
        h.setScadenzaIscrizioni(req.getScadenzaIscrizioni());
        h.setDataInizio(req.getDataInizio());
        h.setDataFine(req.getDataFine());
        h.setLuogo(req.getLuogo());
        h.setPremio(req.getPremio());
        h.setDimensioneMaxTeam(req.getDimensioneMaxTeam());
        h.setDimensioneMinTeam(req.getDimensioneMinTeam());
        h.setNumMaxPersone(req.getNumMaxPersone());
        h.setNumMinPersone(req.getNumMinPersone());
        if (org != null) {
            h.setOrganizzatore(org);
        }
        return h;
    }

    private List<String> missingFieldsForCreate(HackathonRequest req) {
        List<String> missing = new ArrayList<>();
        if (req == null) {
            missing.add("body");
            return missing;
        }
        if (req.getNome() == null || req.getNome().isBlank()) missing.add("nome");
        if (req.getArgomento() == null || req.getArgomento().isBlank()) missing.add("argomento");
        if (req.getDataInizio() == null) missing.add("dataInizio");
        if (req.getDataFine() == null) missing.add("dataFine");
        if (req.getIdOrganizzatore() == null) missing.add("idOrganizzatore");
        return missing;
    }

    private ResponseEntity<Map<String, Object>> invalidOrganizzatore(Integer idOrganizzatore, BaseUtente u) {
        Map<String, Object> details = new HashMap<>();
        details.put("field", "idOrganizzatore");
        details.put("value", idOrganizzatore);
        if (u == null) {
            return badRequest("Organizzatore non trovato", null, details);
        }
        details.put("expected", "Organizzatore");
        details.put("actual", u.getClass().getSimpleName());
        return badRequest("Utente non autorizzato come organizzatore", null, details);
    }

    private ResponseEntity<Map<String, Object>> badRequest(String message,
                                                          List<String> missingFields,
                                                          Map<String, Object> details) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", message);
        if (missingFields != null && !missingFields.isEmpty()) {
            body.put("missingFields", missingFields);
        }
        if (details != null && !details.isEmpty()) {
            body.put("details", details);
        }
        return ResponseEntity.badRequest().body(body);
    }

    private StrategiaPagamento creaStrategia(AssegnaVincitoreRequest req) {
        if ("PAYPAL".equalsIgnoreCase(req.getMetodoPagamento())) {
            return new StrategiaPagamentoPayPal(req.getEmailConto());
        }
        return new StrategiaPagamentoBonifico(req.getIban());
    }
}
