package it.unicam.hackathon.strategy;
import org.springframework.stereotype.Component;

@Component
public class StrategiaPagamentoBonifico implements StrategiaPagamento {

    @Override
    public boolean elaboraPagamento(double importo) {
        return importo > 0;
    }
}