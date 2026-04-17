package it.unicam.hackathon.strategy;

import org.springframework.stereotype.Component;

@Component
public class StrategiaPagamentoPayPal implements StrategiaPagamento {

    @Override
    public boolean elaboraPagamento(double importo) {
        return importo > 0;
    }
}
