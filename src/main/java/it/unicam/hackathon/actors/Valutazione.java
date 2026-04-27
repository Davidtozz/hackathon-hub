package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Valutazione di una sottomissione da parte di un Giudice.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Valutazione extends BaseAttore {
    private String commento;
    private double punteggio;
    private Date dataValutazione = new Date();
    private Sottomissione sottomissione;
    private Giudice giudice;

    public double getPunteggio() {
        return this.punteggio;
    }
}
