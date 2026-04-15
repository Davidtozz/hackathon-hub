package it.unicam.hackathon.strategy;

import org.springframework.stereotype.Component;

@Component
public class PagamentoPayPal implements StrategiaPagamento {

    @Override
    public void elaboraPagamento(double importo) {
        return importo > 0;
    }
}
