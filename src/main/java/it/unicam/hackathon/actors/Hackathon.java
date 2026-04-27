package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.StatoHackathon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Un Hackathon: evento gestito da un Organizzatore, a cui partecipano Team,
 * valutati da Giudici, supportati da Mentori.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Hackathon extends BaseAttore {
    private String nome;
    private String regolamento;
    private String argomento;
    private Date scadenzaIscrizioni;
    private Date dataInizio;
    private Date dataFine;
    private String luogo;
    private Double premio;
    private Integer dimensioneMaxTeam;
    private Integer dimensioneMinTeam;
    private Integer numMaxPersone;
    private Integer numMinPersone;
    private Organizzatore organizzatore;
    private Giudice giudice;
    private List<Mentore> mentori = new ArrayList<>();
    private List<Team> teams = new ArrayList<>();
    private Team vincitore;
    private StatoHackathon stato;
    private List<Sottomissione> sottomissioni = new ArrayList<>();
}
