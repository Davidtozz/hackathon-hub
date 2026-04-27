package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.EnumStatoInvito;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Un Invito inviato da un TeamLeader a un UtenteSenzaTeam
 * per far entrare quest'ultimo in un team.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Invito extends BaseAttore {
    private String titolo;
    private String descrizione;
    private EnumStatoInvito stato = EnumStatoInvito.IN_ATTESA;
    private UtenteSenzaTeam destinatario;
    private TeamLeader mittente;
    private Team team;
}
