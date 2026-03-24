package it.unicam.hackathon.models;

import lombok.Data;

@Data
public final class Invito extends BaseModel{
    private String titolo;
    private String descrizione;
    private EnumStatoInvito stato;
    private UtenteSenzaTeam destinatario;
    private TeamLeader mittente;
}