package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValutazioneRequest {
    private Integer idSottomissione;
    private Integer idGiudice;
    private Double punteggio;
    private String commento;
}
