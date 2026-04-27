package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Giudice;
import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.actors.Valutazione;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

/**
 * Service per la gestione delle valutazioni dei giudici sulle sottomissioni.
 */
@Service
public class ValutazioneService {

    private final ValutazioneRepository valutazioneRepository;

    @Autowired
    public ValutazioneService(ValutazioneRepository valutazioneRepository) {
        this.valutazioneRepository = valutazioneRepository;
    }

    /**
     * Controlla che il punteggio sia in un range valido (0-100).
     */
    public boolean validaPunteggio(double punteggio) {
        return punteggio >= 0.0 && punteggio <= 100.0;
    }

    /**
     * Inserisce una nuova valutazione di un giudice su una sottomissione.
     */
    public Valutazione inserisciValutazione(Giudice giudice, Sottomissione sottomissione,
                                            double punteggio, String commento) {
        if (giudice == null || sottomissione == null) {
            throw new HackathonException("Giudice o sottomissione nulli");
        }
        if (!validaPunteggio(punteggio)) {
            throw new HackathonException("Punteggio fuori range (0-100): " + punteggio);
        }
        Valutazione v = new Valutazione();
        v.setGiudice(giudice);
        v.setSottomissione(sottomissione);
        v.setPunteggio(punteggio);
        v.setCommento(commento);
        return valutazioneRepository.save(v);
    }

    public List<Valutazione> getValutazioniBySottomissione(Integer idSottomissione) {
        return valutazioneRepository.findBySottomissione(idSottomissione);
    }

    /**
     * Calcola il punteggio medio delle valutazioni su una sottomissione.
     * Restituisce 0 se non ci sono valutazioni.
     */
    public double calcolaPunteggioMedio(Integer idSottomissione) {
        List<Valutazione> list = valutazioneRepository.findBySottomissione(idSottomissione);
        OptionalDouble avg = list.stream().mapToDouble(Valutazione::getPunteggio).average();
        return avg.orElse(0.0);
    }
}
