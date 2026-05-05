package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Classe astratta base per i membri dello staff di un Hackathon
 * (Organizzatore, Giudice, Mentore).
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class MembroDelloStaff extends BaseUtente {
    protected Date dataNascita;
    protected Date dataCreazione = new Date();

    /** Hackathon di cui fa parte. JsonIgnoreProperties rompe il loop. */
    @JsonIgnoreProperties({"organizzatore", "giudice", "mentori", "teams", "vincitore", "sottomissioni"})
    protected Hackathon hackathon;
}
