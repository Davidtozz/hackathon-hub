package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SegnalazioneRequest {
    private Integer idTeam;
    private Integer idMentore;
    private Integer idHackathon;
    private String motivazione;
    private String descrizione;
}
