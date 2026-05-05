package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SottomissioneRequest {
    private String link;
    private String descrizione;
    private Integer idTeam;
    private Integer idHackathon;
}
