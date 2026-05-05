package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO per inviare i dati di creazione/modifica di un Hackathon via REST.
 * Si usa al posto dell'entita' Hackathon per evitare di esporre direttamente il modello.
 *
 * Le date sono accettate in formato ISO 8601 (es. "2026-06-01T09:00:00.000+00:00")
 * grazie alla configurazione Jackson di default di Spring Boot.
 */
@Data
@NoArgsConstructor
public class HackathonRequest {
    private Integer id;
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
    private Integer idOrganizzatore;
}
