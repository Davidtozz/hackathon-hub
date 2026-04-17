package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.EnumStato;
import lombok.Data;

@Data
public class Segnalazione extends BaseAttore {
    private String nome;
    private String descrizione;
    private EnumStato statoSegnalazione;
    private Team teamSegnalato;
    private Mentore autoreSegnalazione;


}