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
     *
     * @param vincitore     team che riceve il premio
     * @param importo       importo del premio
     * @param metodo        strategia di pagamento (Bonifico, PayPal, ...)
     * @param intestatario  nome dell'intestatario del pagamento
     * @return true se il pagamento e' andato a buon fine
     */
    public boolean assegnaPremio(Team vincitore, double importo,
                                 StrategiaPagamento metodo, String intestatario) {
        if (vincitore == null || metodo == null || importo <= 0) return false;
        return metodo.pagaPremio(importo, intestatario);
    }
}
