package it.unicam.hackathon.actors;

import java.util.Date;
import java.util.List;

public class Mentore extends MembroDelloStaff {
    protected Date dataDiNascita;
    protected Date dataDiCreazione;

    public List<Ticket> visualizzaTicket() {
        // Implementazione per visualizzare i ticket assegnati al mentore
        return List.of(); // Placeholder
    }

    public String rispondiTicket(Ticket ticket, String risposta) {
        // Implementazione per rispondere a un ticket
        return "Risposta inviata"; // Placeholder
    }

    public Date programmaCall(Team t, Date data) {
        // Implementazione per programmare una call con un team
        return data; // Placeholder
    }

    public Segnalazione creaSegnalazione(Team t, String descrizione) {
        // Implementazione per creare una segnalazione su un team
        return new Segnalazione(); // Placeholder
    }
}