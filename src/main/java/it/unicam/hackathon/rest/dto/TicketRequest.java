package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketRequest {
    private String descrizione;
    private Integer idAutore;        // membro del team che apre il ticket
    private Integer idMentore;       // mentore assegnato
}
