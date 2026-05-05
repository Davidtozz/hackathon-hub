package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Un TeamLeader e' un MembroDelTeam con poteri aggiuntivi:
 * puo' invitare utenti, rimuovere membri, inviare il progetto finale.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TeamLeader extends MembroDelTeam {

    /** Team di cui e' leader. JsonIgnoreProperties rompe il loop con teamLeader. */
    @JsonIgnoreProperties({"teamLeader", "membriDelTeam", "inviti"})
    private Team teamDiAppartenenza;

    /** Hackathon a cui partecipa. Lista semplice senza loop. */
    @JsonIgnoreProperties({"organizzatore", "giudice", "mentori", "teams", "vincitore", "sottomissioni"})
    private List<Hackathon> iMieiHackathon = new ArrayList<>();

    /**
     * Costruttore di promozione: trasforma un UtenteSenzaTeam in TeamLeader
     * copiandone i dati anagrafici.
     */
    public TeamLeader(UtenteSenzaTeam u) {
        this.setId(u.getId());
        this.setNome(u.getNome());
        this.setCognome(u.getCognome());
        this.setEmail(u.getEmail());
        this.setPassword(u.getPassword());
        this.setCellulare(u.getCellulare());
        this.setDataIscrizione(u.getDataIscrizione());
    }
}
