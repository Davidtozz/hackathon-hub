package it.unicam.hackathon.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO per nominare un Giudice o un Mentore in un Hackathon.
 */
@Data
@NoArgsConstructor
public class NominaStaffRequest {
    private Integer idHackathon;
    private Integer idUtente;
}
