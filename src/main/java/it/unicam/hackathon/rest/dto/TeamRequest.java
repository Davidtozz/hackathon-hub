package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO per la creazione di un team via REST.
 */
@Data
@NoArgsConstructor
public class TeamRequest {
    private String nome;
    private Integer idLeader;
}
