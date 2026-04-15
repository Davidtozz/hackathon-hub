package it.unicam.hackathon.models;

import java.util.List;

public class UtenteSenzaTeam extends BaseUtente {
    
    private List<Invito> visualizzaInviti() {
        return List.of(); // logica per recuperare gli inviti dell'utente tramite ticket repository
    }

    private void accettaInvito(Invito i) {
        i.setStato(EnumStatoInvito.ACCETTATO);
    }

    private void rifiutaInvito(Invito i) {
        i.setStato(EnumStatoInvito.RIFIUTATO);
    }

    public Team creaTeam(String nomeTeam) {
        return teamCreationService.createTeam(nomeTeam, this);
    }
}