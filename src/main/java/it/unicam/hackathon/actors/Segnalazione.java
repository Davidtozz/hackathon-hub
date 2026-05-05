package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unicam.hackathon.enums.StatoSegnalazione;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Segnalazione creata da un Mentore verso un team, gestita dall'Organizzatore.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Segnalazione extends BaseAttore {
    private String nome;
    private String descrizione;
    private StatoSegnalazione statoSegnalazione = StatoSegnalazione.APERTA;

    @JsonIgnoreProperties({"membriDelTeam", "teamLeader", "inviti"})
    private Team teamSegnalato;

    @JsonIgnoreProperties({"ticketAssegnati", "hackathon"})
    private Mentore autoreSegnalazione;

    @JsonIgnoreProperties({"organizzatore", "giudice", "mentori", "teams", "vincitore", "sottomissioni"})
    private Hackathon hackathon;

    private Date dataCreazione = new Date();

    /**
     * Gestione della segnalazione (cambio di stato da parte dell'organizzatore).
     */
    public void gestisci(StatoSegnalazione nuovoStato) {
        this.statoSegnalazione = nuovoStato;
    }
}
