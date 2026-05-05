package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Un Giudice valuta le sottomissioni dei team partecipanti all'hackathon.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Giudice extends MembroDelloStaff {

    @JsonIgnoreProperties({"valutazioni", "team", "hackathon"})
    private List<Sottomissione> sottomissioniAssegnate = new ArrayList<>();

    /**
     * Valuta una sottomissione assegnando un punteggio e un feedback.
     */
    public Valutazione valutaSottomissione(Sottomissione s, double punteggio, String feedback) {
        if (s == null) return null;
        Valutazione v = new Valutazione();
        v.setSottomissione(s);
        v.setGiudice(this);
        v.setPunteggio(punteggio);
        v.setCommento(feedback);
        return v;
    }

    /**
     * Restituisce l'elenco delle sottomissioni assegnate a questo giudice.
     */
    public List<Sottomissione> visualizzaSottomissioniAssegnate() {
        return this.sottomissioniAssegnate;
    }
}
