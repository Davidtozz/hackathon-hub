package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Utente registrato che non appartiene ancora a nessun team.
 * Puo' ricevere inviti e decidere di crearne uno proprio.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UtenteSenzaTeam extends BaseUtente {

    private List<Invito> inviti = new ArrayList<>();
}
