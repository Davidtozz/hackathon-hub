package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamLeader extends MembroDelTeam {

    private Team teamDiAppartenenza;
    private Hackathon hackathon;

    public TeamLeader(UtenteSenzaTeam u){
        this.setId(u.getId());
        this.setNome(u.getNome());
        this.setCognome(u.getCognome());
        this.setEmail(u.getEmail());
    }

    public Invito invitaUtente(UtenteSenzaTeam u) throws Exception {
        throw new RuntimeException("Metodo non implementato");
    }

    public void inviaProgetto(Sottomissione s) {
        throw new RuntimeException("Metodo non implementato");
    }

    public void rimuoviMembro(MembroDelTeam m) {
        throw new RuntimeException("Metodo non implementato");
    }
}