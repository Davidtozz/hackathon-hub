package it.unicam.hackathon.actors;

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
    protected Hackathon hackathon;
}
