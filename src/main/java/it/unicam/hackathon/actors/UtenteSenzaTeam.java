package it.unicam.hackathon.actors;

import it.unicam.hackathon.controllers.TeamController;
import it.unicam.hackathon.enums.EnumStatoInvito;
import it.unicam.hackathon.interfaces.BaseUtente;
import it.unicam.hackathon.inviti.AccettaInvitoService;
import it.unicam.hackathon.inviti.IAccettaInvito;
import lombok.NonNull;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class UtenteSenzaTeam extends BaseUtente {
    private final IAccettaInvito accettaInvito = new AccettaInvitoService();

    public List<Invito> accediSezioneInviti() {
        return accettaInvito.visualizzaInviti(getId());
    }

    public void accettaInvito(@NonNull Invito i) {
        i.setStato(EnumStatoInvito.ACCETTATO);
    }

    public void rifiutaInvito(@NonNull Invito i) {
        i.setStato(EnumStatoInvito.RIFIUTATO);
    }

    public TeamLeader creaTeam(@NonNull String nomeTeam) {
        return TeamController.creaTeam(nomeTeam, this);
    }
}