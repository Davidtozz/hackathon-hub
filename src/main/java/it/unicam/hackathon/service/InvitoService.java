package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.enums.EnumStatoInvito;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.InvitoRepository;
import it.unicam.hackathon.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione degli inviti: creazione, invio, risposta.
 */
@Service
public class InvitoService {

    private final InvitoRepository invitoRepository;
    private final UtenteRepository utenteRepository;

    @Autowired
    public InvitoService(InvitoRepository invitoRepository, UtenteRepository utenteRepository) {
        this.invitoRepository = invitoRepository;
        this.utenteRepository = utenteRepository;
    }

    /**
     * Verifica se un utente con quella email esiste gia'.
     */
    public boolean controllaEsistenzaUtente(String email) {
        return utenteRepository.existsByEmail(email);
    }

    /**
     * Crea ed elabora un invito a partire dai dati dell'utente da invitare.
     */
    public Invito elaboraInvito(String nome, String cognome, String email) {
        if (nome == null || cognome == null || email == null
                || nome.isBlank() || cognome.isBlank() || email.isBlank()) {
            throw new HackathonException("Dati invito non validi");
        }
        Invito invito = new Invito();
        invito.setTitolo("Invito nel team");
        invito.setDescrizione("Sei stato invitato a entrare in un team");
        invito.setStato(EnumStatoInvito.IN_ATTESA);

        // Se l'utente esiste, colleghiamo il destinatario
        utenteRepository.findByEmail(email)
                .filter(u -> u instanceof UtenteSenzaTeam)
                .map(u -> (UtenteSenzaTeam) u)
                .ifPresent(invito::setDestinatario);

        return invitoRepository.save(invito);
    }

    /**
     * Invia una email di notifica per l'invito (placeholder).
     */
    public void inviaEmailInvito(Invito i) {
        // placeholder: integrazione con servizio email esterno
    }

    /**
     * Risponde a un invito (accetta o rifiuta).
     */
    public void rispondiInvito(Integer idInvito, boolean accetta) {
        Invito i = invitoRepository.findById(idInvito)
                .orElseThrow(() -> new HackathonException("Invito non trovato: " + idInvito));
        i.setStato(accetta ? EnumStatoInvito.ACCETTATO : EnumStatoInvito.RIFIUTATO);
        invitoRepository.save(i);
    }

    /**
     * Visualizza tutti gli inviti di un utente.
     */
    public List<Invito> visualizzaInvitiByUtente(Integer idUtente) {
        return invitoRepository.findByDestinatario(idUtente);
    }
}
