package it.unicam.hackathon.actors;

import it.unicam.hackathon.enums.EnumStato;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Ticket di supporto che un membro di un team puo' aprire verso un Mentore.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Ticket extends BaseAttore {
    private String descrizione;
    private String risposta;
    private EnumStato stato = EnumStato.APERTO;
    private Mentore mentoreAssegnato;
    private MembroDelTeam autore;

    /**
     * Risolve il ticket impostando la risposta e lo stato CHIUSO.
     */
    public void risolvi(String risposta) {
        this.risposta = risposta;
        this.stato = EnumStato.CHIUSO;
    }
}
