package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties({"hackathon", "ticketAssegnati"})
    private Organizzatore organizzatore;

    @JsonIgnoreProperties({"hackathon", "ticketAssegnati"})
    private Giudice giudice;

    @JsonIgnoreProperties({"hackathon", "ticketAssegnati"})
    private List<Mentore> mentori = new ArrayList<>();

    @JsonIgnoreProperties({"membriDelTeam", "teamLeader", "inviti"})
    private List<Team> teams = new ArrayList<>();

    @JsonIgnoreProperties({"membriDelTeam", "teamLeader", "inviti"})
    private Team vincitore;

    private StatoHackathon stato;

    @JsonIgnoreProperties({"team"})
    private List<Sottomissione> sottomissioni = new ArrayList<>();
}
