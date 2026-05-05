package it.unicam.hackathon.actors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Sottomissione del progetto di un Team durante un Hackathon.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Sottomissione extends BaseAttore {
    private String linkRepository;
    private Date dataConsegna = new Date();
    private String descrizione;

    @JsonIgnoreProperties({"membriDelTeam", "teamLeader", "inviti"})
    private Team team;

    @JsonIgnoreProperties({"organizzatore", "giudice", "mentori", "teams", "vincitore", "sottomissioni"})
    private Hackathon hackathon;

    @JsonIgnoreProperties({"sottomissione", "giudice"})
    private List<Valutazione> valutazioni = new ArrayList<>();

    public String getLink() {
        return this.linkRepository;
    }

    public void setLink(String link) {
        this.linkRepository = link;
    }
}
