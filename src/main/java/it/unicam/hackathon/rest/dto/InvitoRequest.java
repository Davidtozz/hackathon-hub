package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitoRequest {
    private String nome;
    private String cognome;
    private String email;
    private Integer idTeam;
}
