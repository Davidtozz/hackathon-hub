package it.unicam.hackathon.builders;

import it.unicam.hackathon.actors.Hackathon;
import it.unicam.hackathon.actors.Organizzatore;
import it.unicam.hackathon.enums.StatoHackathon;
import it.unicam.hackathon.exception.HackathonException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Implementazione concreta dell' {@link IBuilder} per la costruzione di un Hackathon.
 *
 * Realizza il Builder Pattern definito dall'interfaccia {@link IBuilder}, ed espone
 * inoltre alcuni metodi di convenienza extra (luogo, organizzatore, scadenzaIscrizioni,
 * numero massimo/minimo di persone) che non fanno parte del contratto base ma servono
 * a popolare campi specifici dell'Hackathon.
 */
@Component
public class HackathonBuilder implements IBuilder {

    private Hackathon hackathon = new Hackathon();

    // ============================================================
    // Metodi del contratto IBuilder (diagramma di progetto)
    // ============================================================

    @Override
    public HackathonBuilder reset() {
        this.hackathon = new Hackathon();
        return this;
    }

    @Override
    public HackathonBuilder setNome(String nome) {
        hackathon.setNome(nome);
        return this;
    }

    @Override
    public HackathonBuilder setDate(Date inizio, Date fine) {
        hackathon.setDataInizio(inizio);
        hackathon.setDataFine(fine);
        return this;
    }

    @Override
    public HackathonBuilder setRegolamento(String regolamento) {
        hackathon.setRegolamento(regolamento);
        return this;
    }

    @Override
    public HackathonBuilder setRegoleTeam(int min, int max) {
        hackathon.setDimensioneMinTeam(min);
        hackathon.setDimensioneMaxTeam(max);
        return this;
    }

    @Override
    public HackathonBuilder setPremio(double premio) {
        hackathon.setPremio(premio);
        return this;
    }

    @Override
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

    // ============================================================
    // Metodi extra (non nel contratto IBuilder ma utili al sistema)
    // ============================================================

    public HackathonBuilder argomento(String argomento) {
        hackathon.setArgomento(argomento);
        return this;
    }

    public HackathonBuilder luogo(String luogo) {
        hackathon.setLuogo(luogo);
        return this;
    }

    public HackathonBuilder scadenzaIscrizioni(Date scadenzaIscrizioni) {
        hackathon.setScadenzaIscrizioni(scadenzaIscrizioni);
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

    // ============================================================
    // Alias dei vecchi metodi (per compatibilita' con il codice esistente)
    // ============================================================

    /** Alias di {@link #setNome(String)} per il vecchio stile fluente. */
    public HackathonBuilder nome(String nome) {
        return setNome(nome);
    }

    /** Imposta solo la data di inizio (variante senza data fine). */
    public HackathonBuilder dataInizio(Date dataInizio) {
        hackathon.setDataInizio(dataInizio);
        return this;
    }

    /** Imposta solo la data di fine (variante senza data inizio). */
    public HackathonBuilder dataFine(Date dataFine) {
        hackathon.setDataFine(dataFine);
        return this;
    }

    /** Alias di setRegoleTeam(min, max) limitato al minimo. */
    public HackathonBuilder dimensioneMinTeam(Integer min) {
        hackathon.setDimensioneMinTeam(min);
        return this;
    }

    /** Alias di setRegoleTeam(min, max) limitato al massimo. */
    public HackathonBuilder dimensioneMaxTeam(Integer max) {
        hackathon.setDimensioneMaxTeam(max);
        return this;
    }

    /** Alias di {@link #setRegolamento(String)}. */
    public HackathonBuilder regolamento(String regolamento) {
        return setRegolamento(regolamento);
    }

    /** Alias di {@link #setPremio(double)}. */
    public HackathonBuilder premio(Double premio) {
        if (premio == null) return this;
        return setPremio(premio);
    }
}
