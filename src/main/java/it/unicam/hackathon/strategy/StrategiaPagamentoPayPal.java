package it.unicam.hackathon.strategy;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Implementazione della Strategy di pagamento tramite PayPal.
 */
@Data
@Component
public class StrategiaPagamentoPayPal implements StrategiaPagamento {

    private String emailConto;

    @Override
    public boolean elaboraPagamento(double importo) {
        // placeholder: in un sistema reale invocherebbe le API PayPal
        return importo > 0;
    }
}
