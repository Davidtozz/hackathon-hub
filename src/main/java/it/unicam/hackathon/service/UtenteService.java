package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione degli utenti: registrazione, autenticazione, profilo.
 */
@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * Verifica che i dati anagrafici minimi siano presenti.
     */
    public boolean verificaDati(BaseUtente dati) {
        if (dati == null) return false;
        return dati.getNome() != null && !dati.getNome().isBlank()
                && dati.getCognome() != null && !dati.getCognome().isBlank()
                && dati.getEmail() != null && !dati.getEmail().isBlank()
                && dati.getPassword() != null && !dati.getPassword().isBlank();
    }

    /**
     * Verifica se esiste gia' un utente con quell'email.
     */
    public boolean verificaEmailEsistente(String email) {
        return utenteRepository.existsByEmail(email);
    }

    /**
     * Registra un nuovo utente se i dati sono validi e l'email non esiste.
     */
    public boolean registraNuovoUtente(BaseUtente dati) {
        if (!verificaDati(dati)) {
            throw new HackathonException("Dati utente non validi");
        }
        if (verificaEmailEsistente(dati.getEmail())) {
            throw new HackathonException("Email gia' registrata: " + dati.getEmail());
        }
        utenteRepository.save(dati);
        return true;
    }

    /**
     * Crea/salva il profilo utente (alias semantico).
     */
    public BaseUtente creaProfiloUtente(BaseUtente dati) {
        return utenteRepository.save(dati);
    }

    /**
     * Autentica un utente: controlla email e password.
     */
    public BaseUtente autenticaUtente(String email, String password) {
        return utenteRepository.findByEmail(email)
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password))
                .orElseThrow(() -> new HackathonException("Credenziali non valide"));
    }

    /**
     * Restituisce il profilo di un utente dato il suo id.
     */
    public BaseUtente getProfilo(Integer id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Utente non trovato: " + id));
    }
}
