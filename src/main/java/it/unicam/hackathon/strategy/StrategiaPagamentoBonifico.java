package it.unicam.hackathon.strategy;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Implementazione della Strategy di pagamento tramite bonifico bancario.
 */
@Data
@Component
public class StrategiaPagamentoBonifico implements StrategiaPagamento {

    private String iban;

    @Override
    public boolean elaboraPagamento(double importo) {
        // placeholder: in un sistema reale invocherebbe un gateway bancario
        return importo > 0;
    }
}
