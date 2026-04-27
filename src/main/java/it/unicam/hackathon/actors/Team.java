package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Un Team: gruppo di utenti guidato da un TeamLeader, iscritto a un Hackathon.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Team extends BaseAttore {
    private String nome;
    private TeamLeader teamLeader;
    private List<MembroDelTeam> membriDelTeam = new ArrayList<>();
    private List<Invito> inviti = new ArrayList<>();
    private Date dataCreazione = new Date();

    public Team(String nome, TeamLeader teamLeader) {
        this.nome = nome;
        this.teamLeader = teamLeader;
    }

    /**
     * Aggiunge un membro al team se non gia' presente.
     */
    public void aggiungiMembro(MembroDelTeam membro) {
        if (membro == null) return;
        if (!this.membriDelTeam.contains(membro)) {
            this.membriDelTeam.add(membro);
        }
    }

    /**
     * Rimuove un membro dal team.
     */
    public void rimuoviMembro(MembroDelTeam membro) {
        this.membriDelTeam.remove(membro);
    }

    /**
     * Restituisce il numero totale di membri (incluso il leader).
     */
    public int getNumeroMembri() {
        return membriDelTeam.size() + (teamLeader != null ? 1 : 0);
    }
}
