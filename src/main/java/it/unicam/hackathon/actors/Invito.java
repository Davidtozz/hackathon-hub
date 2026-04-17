package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.EnumStatoInvito;
import lombok.Data;

@Data
public final class Invito extends BaseAttore {
    private String titolo;
    private String descrizione;
    private EnumStatoInvito stato;
    private UtenteSenzaTeam destinatario;
    private TeamLeader mittente;
}