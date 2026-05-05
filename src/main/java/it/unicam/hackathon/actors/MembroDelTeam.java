package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Un utente che e' membro di un Team.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MembroDelTeam extends BaseUtente {

    /** Team di appartenenza. JsonIgnoreProperties rompe il loop di serializzazione. */
    @JsonIgnoreProperties({"membriDelTeam", "teamLeader", "inviti"})
    private Team team;

    /**
     * Abbandona il team attuale.
     */
    public void abbandonaTeam() {
        if (this.team != null) {
            this.team.rimuoviMembro(this);
            this.team = null;
        }
    }

    /**
     * Invoca l'aggiornamento della sottomissione del proprio team.
     */
    public void aggiornaSottomissione(Sottomissione s) {
        // la logica applicativa e' gestita dal SottomissioneService
    }
}
