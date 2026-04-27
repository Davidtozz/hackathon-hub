package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.TeamLeader;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione dei team: creazione, aggiunta membri, eliminazione.
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Verifica se esiste gia' un team con quel nome.
     */
    public boolean verificaEsistenza(String nomeTeam) {
        return teamRepository.existsByNome(nomeTeam);
    }

    /**
     * Crea un nuovo team. Il leader deve essere valorizzato.
     */
    public Team creaNuovoTeam(String nome, TeamLeader leader) {
        if (nome == null || nome.isBlank()) {
            throw new HackathonException("Nome team obbligatorio");
        }
        if (leader == null) {
            throw new HackathonException("TeamLeader obbligatorio");
        }
        if (verificaEsistenza(nome)) {
            throw new HackathonException("Nome team gia' esistente: " + nome);
        }
        Team team = new Team(nome, leader);
        leader.setTeamDiAppartenenza(team);
        return teamRepository.save(team);
    }

    /**
     * Aggiunge un membro a un team esistente.
     */
    public void aggiungiMembro(Integer idTeam, MembroDelTeam membro) {
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HackathonException("Team non trovato: " + idTeam));
        team.aggiungiMembro(membro);
        membro.setTeam(team);
        teamRepository.save(team);
    }

    /**
     * Notifica i membri del team di un evento.
     */
    public void inviaNotifiche(Team team) {
        // placeholder: in un sistema reale si invierebbero email/push notifications
    }

    /**
     * Elimina un team.
     */
    public void eliminaTeam(Integer idTeam) {
        if (!teamRepository.existsById(idTeam)) {
            throw new HackathonException("Team non trovato: " + idTeam);
        }
        teamRepository.deleteById(idTeam);
    }
}
