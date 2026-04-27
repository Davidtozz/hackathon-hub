package it.unicam.hackathon.actors;

import lombok.Data;

/**
 * Classe base per tutte le entita' del dominio che hanno un identificativo.
 */
@Data
public abstract class BaseAttore {
    protected Integer id;
}
