package it.unicam.hackathon.strategy;

/**
 * Strategy Pattern: interfaccia per i diversi metodi di pagamento del premio.
 *
 * Le implementazioni concrete realizzano logiche di pagamento differenti
 * (bonifico, PayPal, ecc.) mantenendo la stessa firma di interfaccia.
 */
public interface StrategiaPagamento {

    /**
     * Elabora il pagamento del premio verso il destinatario indicato.
     *
     * @param importo       importo da pagare (deve essere positivo)
     * @param intestatario  intestatario del pagamento
     * @return true se il pagamento e' andato a buon fine, false altrimenti
     */
    boolean pagaPremio(double importo, String intestatario);
}
