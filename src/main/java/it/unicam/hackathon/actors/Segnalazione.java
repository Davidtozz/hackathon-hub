package it.unicam.hackathon.models;

import it.unicam.hackathon.models.enums.EnumStato;
import lombok.Data;

@Data
public class Segnalazione extends BaseModel {
    private String nome;
    private String descrizione;
    private EnumStato statoSegnalazione;
    private Team teamSegnalato;
    private Mentore autoreSegnalazione;


}