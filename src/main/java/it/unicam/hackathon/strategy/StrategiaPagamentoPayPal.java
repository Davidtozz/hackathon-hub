package it.unicam.hackathon.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Implementazione della Strategy di pagamento tramite PayPal.
 *
 * Non e' un bean Spring: viene istanziata di volta in volta con i dati
 * specifici dell'operazione (email del conto PayPal).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategiaPagamentoPayPal implements StrategiaPagamento {

    private String emailConto;

    @Override
    public boolean pagaPremio(double importo, String intestatario) {
        // placeholder: in un sistema reale invocherebbe le API PayPal
        if (importo <= 0 || intestatario == null || intestatario.isBlank()) {
            return false;
        }
        if (emailConto == null || emailConto.isBlank()) {
            return false;
        }
        // simulazione di pagamento PayPal riuscito
        return true;
    }
}
