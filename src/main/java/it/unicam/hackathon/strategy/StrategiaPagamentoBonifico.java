package it.unicam.hackathon.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Implementazione della Strategy di pagamento tramite bonifico bancario.
 *
 * Non e' un bean Spring: viene istanziata di volta in volta con i dati
 * specifici dell'operazione (IBAN del destinatario).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategiaPagamentoBonifico implements StrategiaPagamento {

    private String iban;

    @Override
    public boolean pagaPremio(double importo, String intestatario) {
        // placeholder: in un sistema reale invocherebbe un gateway bancario
        if (importo <= 0 || intestatario == null || intestatario.isBlank()) {
            return false;
        }
        if (iban == null || iban.isBlank()) {
            return false;
        }
        // simulazione di bonifico riuscito
        return true;
    }
}
