package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Invito;
import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.UtenteSenzaTeam;
import it.unicam.hackathon.enums.EnumStatoInvito;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.InvitoRepository;
import it.unicam.hackathon.repository.TeamRepository;
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
    private final TeamRepository teamRepository;

    @Autowired
    public InvitoService(InvitoRepository invitoRepository,
                         UtenteRepository utenteRepository,
                         TeamRepository teamRepository) {
        this.invitoRepository = invitoRepository;
        this.utenteRepository = utenteRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Verifica se un utente con quella email esiste gia'.
     */
    public boolean controllaEsistenzaUtente(String email) {
        return utenteRepository.existsByEmail(email);
    }

    /**
     * Crea ed elabora un invito a partire dai dati dell'utente da invitare e dall'idTeam.
     */
    public Invito elaboraInvito(String nome, String cognome, String email, Integer idTeam) {
        if (nome == null || cognome == null || email == null
                || nome.isBlank() || cognome.isBlank() || email.isBlank()) {
            throw new HackathonException("Dati invito non validi (nome/cognome/email mancanti)");
        }
        if (idTeam == null) {
            throw new HackathonException("idTeam obbligatorio per creare un invito");
        }

        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HackathonException("Team non trovato con id " + idTeam));

        Invito invito = new Invito();
        invito.setTitolo("Invito nel team " + team.getNome());
        invito.setDescrizione("Sei stato invitato a entrare nel team " + team.getNome());
        invito.setStato(EnumStatoInvito.IN_ATTESA);
        invito.setTeam(team);
        if (team.getTeamLeader() != null) {
            invito.setMittente(team.getTeamLeader());
        }

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
     * Se accettato, l'utente viene trasformato in MembroDelTeam e aggiunto al team.
     */
    public void rispondiInvito(Integer idInvito, boolean accetta) {
        Invito i = invitoRepository.findById(idInvito)
                .orElseThrow(() -> new HackathonException("Invito non trovato: " + idInvito));

        if (!accetta) {
            i.setStato(EnumStatoInvito.RIFIUTATO);
            invitoRepository.save(i);
            return;
        }

        UtenteSenzaTeam destinatario = i.getDestinatario();
        if (destinatario == null) {
            throw new HackathonException(
                    "L'invito non ha un destinatario registrato. "
                            + "L'utente invitato deve prima registrarsi al sistema.");
        }
        Team team = i.getTeam();
        if (team == null) {
            throw new HackathonException("L'invito non e' associato a nessun team.");
        }

        Team teamReale = teamRepository.findById(team.getId())
                .orElseThrow(() -> new HackathonException("Team non piu' esistente: " + team.getId()));

        MembroDelTeam membro = new MembroDelTeam();
        membro.setId(destinatario.getId());
        membro.setNome(destinatario.getNome());
        membro.setCognome(destinatario.getCognome());
        membro.setEmail(destinatario.getEmail());
        membro.setPassword(destinatario.getPassword());
        membro.setCellulare(destinatario.getCellulare());
        membro.setDataIscrizione(destinatario.getDataIscrizione());
        membro.setTeam(teamReale);

        teamReale.aggiungiMembro(membro);
        teamRepository.save(teamReale);

        utenteRepository.save(membro);

        i.setStato(EnumStatoInvito.ACCETTATO);
        invitoRepository.save(i);
    }

    /**
     * Visualizza tutti gli inviti di un utente.
     */
    public List<Invito> visualizzaInvitiByUtente(Integer idUtente) {
        return invitoRepository.findByDestinatario(idUtente);
    }
}