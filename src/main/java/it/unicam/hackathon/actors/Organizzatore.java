package it.unicam.hackathon.actors;

import it.unicam.hackathon.strategy.StrategiaPagamento;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Organizzatore extends MembroDelloStaff {

    /**
     * Assegna il premio al team vincitore usando una strategia di pagamento.
     * Delega il pagamento alla Strategy scelta.
     */
    public boolean assegnaPremio(Team vincitore, double importo, StrategiaPagamento metodo) {
        if (vincitore == null || metodo == null || importo <= 0) return false;
        return metodo.elaboraPagamento(importo);
    }
}
