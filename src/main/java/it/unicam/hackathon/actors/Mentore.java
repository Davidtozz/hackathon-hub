package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Un Mentore supporta i team durante l'hackathon, puo' gestire ticket
 * e creare segnalazioni verso l'organizzatore.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Mentore extends MembroDelloStaff {

    private List<Ticket> ticketAssegnati = new ArrayList<>();

    /**
     * Restituisce la lista dei ticket assegnati al mentore.
     */
    public List<Ticket> visualizzaTicket() {
        return this.ticketAssegnati;
    }

    /**
     * Risponde a un ticket specifico.
     */
    public String rispondiTicket(Ticket ticket, String risposta) {
        if (ticket == null) return "Ticket non valido";
        ticket.risolvi(risposta);
        return "Risposta inviata";
    }

    /**
     * Programma una call con un team.
     */
    public Date programmaCall(Team t, Date data) {
        // la gestione reale del calendario sarebbe delegata a un servizio esterno
        return data;
    }

    /**
     * Crea una segnalazione su un team con una motivazione.
     * La segnalazione viene poi gestita dal SegnalazioneService.
     */
    public Segnalazione creaSegnalazione(Team t, String motivazione) {
        Segnalazione s = new Segnalazione();
        s.setTeamSegnalato(t);
        s.setDescrizione(motivazione);
        s.setAutoreSegnalazione(this);
        return s;
    }
}
