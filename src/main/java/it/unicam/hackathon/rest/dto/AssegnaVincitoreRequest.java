package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssegnaVincitoreRequest {
    private Integer idTeam;
    private Double importoPremio;
    private String metodoPagamento;       // "BONIFICO" o "PAYPAL"
    private String intestatario;
    private String iban;                  // se BONIFICO
    private String emailConto;            // se PAYPAL
}
