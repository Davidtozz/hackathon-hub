package it.unicam.hackathon.builders;

import it.unicam.hackathon.actors.Hackathon;

import java.util.Date;

/**
 * Interfaccia del Builder Pattern per la costruzione di un Hackathon.
 *
 * Definisce i passi (set*) che un costruttore concreto deve fornire
 * per assemblare un Hackathon, oltre ai metodi reset() e build().
 *
 * Le implementazioni concrete (es. {@link HackathonBuilder}) realizzano
 * questa interfaccia e popolano l'oggetto in costruzione.
 */
public interface IBuilder {

    /**
     * Riporta il builder allo stato iniziale, scartando l'oggetto in costruzione.
     */
    IBuilder reset();

    /** Imposta il nome dell'hackathon. */
    IBuilder setNome(String nome);

    /** Imposta la data di inizio e di fine dell'hackathon. */
    IBuilder setDate(Date inizio, Date fine);

    /** Imposta il regolamento dell'hackathon. */
    IBuilder setRegolamento(String regolamento);

    /** Imposta le regole di composizione dei team (numero minimo e massimo di membri). */
    IBuilder setRegoleTeam(int min, int max);

    /** Imposta il valore del premio finale. */
    IBuilder setPremio(double premio);

    /**
     * Conclude la costruzione e restituisce l'oggetto Hackathon.
     */
    Hackathon build();
}
