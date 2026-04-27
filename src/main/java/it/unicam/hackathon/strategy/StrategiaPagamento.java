package it.unicam.hackathon.strategy;

/**
 * Strategy Pattern: interfaccia per diversi metodi di pagamento del premio.
 */
public interface StrategiaPagamento {
    boolean elaboraPagamento(double importo);
}
