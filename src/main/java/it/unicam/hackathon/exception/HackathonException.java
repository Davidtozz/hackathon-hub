package it.unicam.hackathon.exception;

/**
 * Eccezione custom per errori di business logic dell'applicazione.
 */
public class HackathonException extends RuntimeException {

    public HackathonException(String message) {
        super(message);
    }

    public HackathonException(String message, Throwable cause) {
        super(message, cause);
    }
}
