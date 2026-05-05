package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.MembroDelTeam;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.actors.TeamLeader;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione dei team: creazione, aggiunta membri, eliminazione.
 * Nella 4a iterazione vengono aggiunti i metodi per recuperare i team di un leader,
 * rimuovere team e abbandonare il gruppo.
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
     * Elimina un team. Ripulisce anche il team di appartenenza del leader e dei membri.
     */
    public void eliminaTeam(Integer idTeam) {
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HackathonException("Team non trovato: " + idTeam));
        if (team.getTeamLeader() != null) {
            team.getTeamLeader().setTeamDiAppartenenza(null);
        }
        if (team.getMembriDelTeam() != null) {
            for (MembroDelTeam m : team.getMembriDelTeam()) {
                m.setTeam(null);
            }
        }
        teamRepository.cancellaPerId(idTeam);
    }

    // ============================================================
    // === METODI AGGIUNTI NELLA 4a ITERAZIONE                  ===
    // ============================================================

    /**
     * Restituisce tutti i team gestiti da un certo TeamLeader.
     * Sequence diagram: "Eliminazione Team".
     */
    public List<Team> ottieniTeamsPerLeader(Integer idLeader) {
        if (idLeader == null) {
            throw new HackathonException("Id leader obbligatorio");
        }
        return teamRepository.findByLeader(idLeader);
    }

    /**
     * Rimuove un team dal sistema. Alias semantico di eliminaTeam(),
     * mantenuto per coerenza con il sequence diagram della 4a iterazione.
     */
    public void rimuoviTeam(Integer idTeam) {
        eliminaTeam(idTeam);
    }

    /**
     * Rimuove un membro dal team a cui appartiene.
     * Aggiorna sia il modello sia la persistenza.
     * Sequence diagram: "Lascia Gruppo".
     */
    public void rimuoviMembroDaTeam(Integer idMembro) {
        if (idMembro == null) {
            throw new HackathonException("Id membro obbligatorio");
        }
        Team team = teamRepository.findByMembro(idMembro)
                .orElseThrow(() -> new HackathonException(
                        "Nessun team trovato per il membro: " + idMembro));
        MembroDelTeam membro = team.getMembriDelTeam().stream()
                .filter(m -> idMembro.equals(m.getId()))
                .findFirst()
                .orElseThrow(() -> new HackathonException(
                        "Membro non trovato nel team: " + idMembro));
        team.rimuoviMembro(membro);
        membro.setTeam(null);
        teamRepository.save(team);
    }
}
