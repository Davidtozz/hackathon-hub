package it.unicam.hackathon.builders;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.enums.StatoHackathon;
import it.unicam.hackathon.exception.HackathonException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Builder Pattern per la creazione di un Hackathon in modo fluente.
 */
@Component
public class HackathonBuilder {

    private Hackathon hackathon = new Hackathon();

    public HackathonBuilder reset() {
        this.hackathon = new Hackathon();
        return this;
    }

    public HackathonBuilder nome(String nome) {
        hackathon.setNome(nome);
        return this;
    }

    public HackathonBuilder argomento(String argomento) {
        hackathon.setArgomento(argomento);
        return this;
    }

    public HackathonBuilder regolamento(String regolamento) {
        hackathon.setRegolamento(regolamento);
        return this;
    }

    public HackathonBuilder luogo(String luogo) {
        hackathon.setLuogo(luogo);
        return this;
    }

    public HackathonBuilder premio(Double premio) {
        hackathon.setPremio(premio);
        return this;
    }

    public HackathonBuilder scadenzaIscrizioni(Date scadenzaIscrizioni) {
        hackathon.setScadenzaIscrizioni(scadenzaIscrizioni);
        return this;
    }

    public HackathonBuilder dataInizio(Date dataInizio) {
        hackathon.setDataInizio(dataInizio);
        return this;
    }

    public HackathonBuilder dataFine(Date dataFine) {
        hackathon.setDataFine(dataFine);
        return this;
    }

    public HackathonBuilder dimensioneMaxTeam(Integer max) {
        hackathon.setDimensioneMaxTeam(max);
        return this;
    }

    public HackathonBuilder dimensioneMinTeam(Integer min) {
        hackathon.setDimensioneMinTeam(min);
        return this;
    }

    public HackathonBuilder numMaxPersone(Integer max) {
        hackathon.setNumMaxPersone(max);
        return this;
    }

    public HackathonBuilder numMinPersone(Integer min) {
        hackathon.setNumMinPersone(min);
        return this;
    }

    public HackathonBuilder organizzatore(Organizzatore organizzatore) {
        hackathon.setOrganizzatore(organizzatore);
        return this;
    }

    public Hackathon build() {
        if (hackathon.getNome() == null || hackathon.getOrganizzatore() == null
                || hackathon.getDataInizio() == null || hackathon.getDataFine() == null
                || hackathon.getScadenzaIscrizioni() == null) {
            throw new HackathonException("Campi obbligatori mancanti per la creazione dell'Hackathon.");
        }
        hackathon.setStato(StatoHackathon.ISCRIZIONI_APERTE);
        Hackathon built = this.hackathon;
        // reset automatico per riutilizzo del builder
        this.hackathon = new Hackathon();
        return built;
    }
}
