package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Team extends BaseAttore {
    private String nome;
    private TeamLeader teamLeader;
    private MembroDelTeam[] membriDelTeam;
    private List<Invito> inviti;

    public Team(String nome, TeamLeader teamLeader) {
        this.nome = nome;
        this.teamLeader = teamLeader;
    }
/*
    public Team(String nome, UtenteSenzaTeam teamLeader) {
        this.nome = nome;
        this.teamLeader = new teamLeader;
    }
*/

    public void aggiungiMembro(MembroDelTeam membro) {
        // logica per aggiungere un membro al team

    }
}