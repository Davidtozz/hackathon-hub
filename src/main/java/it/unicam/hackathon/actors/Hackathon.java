package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.StatoHackathon;
import lombok.Data;

import java.util.Date;

@Data
public class Hackathon {
    private Integer id;
    private String nome;
    private String regolamento;
    private String argomento;
    private Date scadenzaIscrizioni;
    private Date dataInizio;
    private Date dataFine;
    private String luogo;
    private Double premio;
    private Integer dimesioneMaxTeam;
    private Integer dimesioneMinTeam;
    private Integer numMaxPersone;
    private Integer numMinPersone;
    private MembroDelloStaff organizzatore;
    private MembroDelloStaff giudice;
    private MembroDelloStaff[] mentori;
    private Team[] teams;
    private Team vincitore;
    private StatoHackathon stato;
    private Sottomissione[] sottomissioni;
}
