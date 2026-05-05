package it.unicam.hackathon.rest;

import it.unicam.hackathon.actors.Giudice;
import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.actors.Valutazione;
import it.unicam.hackathon.repository.SottomissioneRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import it.unicam.hackathon.rest.dto.ValutazioneRequest;
import it.unicam.hackathon.service.ValutazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST API per la gestione delle valutazioni dei giudici sulle sottomissioni.
 *
 * Endpoint base: /api/valutazioni
 */
@RestController
@RequestMapping("/api/valutazioni")
public class ValutazioneRestController {

    private final ValutazioneService valutazioneService;
    private final UtenteRepository utenteRepository;
    private final SottomissioneRepository sottomissioneRepository;

    @Autowired
    public ValutazioneRestController(ValutazioneService valutazioneService,
                                     UtenteRepository utenteRepository,
                                     SottomissioneRepository sottomissioneRepository) {
        this.valutazioneService = valutazioneService;
        this.utenteRepository = utenteRepository;
        this.sottomissioneRepository = sottomissioneRepository;
    }

    /** POST /api/valutazioni — un giudice valuta una sottomissione. */
    @PostMapping
    public ResponseEntity<Valutazione> inserisciValutazione(@RequestBody ValutazioneRequest req) {
        Giudice giudice = (Giudice) utenteRepository.findById(req.getIdGiudice()).orElse(null);
        Sottomissione s = sottomissioneRepository.findById(req.getIdSottomissione()).orElse(null);
        if (giudice == null || s == null) return ResponseEntity.badRequest().build();
        Valutazione v = valutazioneService.inserisciValutazione(giudice, s, req.getPunteggio(), req.getCommento());
        return ResponseEntity.ok(v);
    }

    /** GET /api/valutazioni/sottomissione/{id} — valutazioni di una sottomissione. */
    @GetMapping("/sottomissione/{id}")
    public ResponseEntity<List<Valutazione>> getBySottomissione(@PathVariable Integer id) {
        return ResponseEntity.ok(valutazioneService.getValutazioniBySottomissione(id));
    }

    /** GET /api/valutazioni/sottomissione/{id}/media — punteggio medio. */
    @GetMapping("/sottomissione/{id}/media")
    public ResponseEntity<Map<String, Double>> getPunteggioMedio(@PathVariable Integer id) {
        double media = valutazioneService.calcolaPunteggioMedio(id);
        return ResponseEntity.ok(Map.of("punteggioMedio", media));
    }
}
