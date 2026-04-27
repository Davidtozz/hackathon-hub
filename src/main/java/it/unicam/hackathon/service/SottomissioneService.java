package it.unicam.hackathon.service;

import it.unicam.hackathon.actors.Sottomissione;
import it.unicam.hackathon.actors.Team;
import it.unicam.hackathon.exception.HackathonException;
import it.unicam.hackathon.repository.SottomissioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service per la gestione delle sottomissioni dei team.
 */
@Service
public class SottomissioneService {

    private final SottomissioneRepository sottomissioneRepository;

    @Autowired
    public SottomissioneService(SottomissioneRepository sottomissioneRepository) {
        this.sottomissioneRepository = sottomissioneRepository;
    }

    /**
     * Verifica che il link della repository sia valido.
     */
    public boolean verificaLinkValido(String link) {
        if (link == null || link.isBlank()) return false;
        return link.startsWith("http://") || link.startsWith("https://");
    }

    /**
     * Crea una nuova sottomissione per un team.
     */
    public Sottomissione creaSottomissione(Team team, String link) {
        if (team == null) throw new HackathonException("Team obbligatorio");
        if (!verificaLinkValido(link)) {
            throw new HackathonException("Link repository non valido");
        }
        Sottomissione s = new Sottomissione();
        s.setTeam(team);
        s.setLink(link);
        return sottomissioneRepository.save(s);
    }

    /**
     * Aggiorna il link di una sottomissione esistente.
     */
    public Sottomissione aggiornaSottomissione(Integer id, String link) {
        Sottomissione s = sottomissioneRepository.findById(id)
                .orElseThrow(() -> new HackathonException("Sottomissione non trovata: " + id));
        if (!verificaLinkValido(link)) {
            throw new HackathonException("Link repository non valido");
        }
        s.setLink(link);
        return sottomissioneRepository.save(s);
    }

    public List<Sottomissione> getSottomissioniByHackathon(Integer idHackathon) {
        return sottomissioneRepository.findByHackathon(idHackathon);
    }

    public List<Sottomissione> getSottomissioniByTeam(Integer idTeam) {
        return sottomissioneRepository.findByTeam(idTeam);
    }
}
