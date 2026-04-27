package it.unicam.hackathon.controllers;

import it.unicam.hackathon.actors.BaseUtente;
import it.unicam.hackathon.interfaces.IRegistrazione;
import it.unicam.hackathon.interfaces.IVisualizzaProfilo;
import it.unicam.hackathon.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller per la gestione degli utenti: registrazione, login, visualizzazione profilo.
 * Implementa le interfacce boundary IRegistrazione e IVisualizzaProfilo.
 */
@Component
public class UtenteController implements IRegistrazione, IVisualizzaProfilo {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    /**
     * Registra un utente delegando al service.
     */
    public void registraUtente(BaseUtente dati) {
        utenteService.registraNuovoUtente(dati);
    }

    /**
     * Login: delega l'autenticazione al service.
     */
    public BaseUtente logIn(String email, String password) {
        return utenteService.autenticaUtente(email, password);
    }

    public void logOut() {
        // placeholder: in un sistema con sessione/JWT, qui si invaliderebbe il token
    }

    // === IRegistrazione ===

    @Override
    public void mostraModuloRegistrazione() {
        // boundary: in un'app reale renderizzerebbe un form
    }

    @Override
    public void inserimentoDati(BaseUtente dati) {
        utenteService.registraNuovoUtente(dati);
    }

    @Override
    public void annullaRegistrazione() {
        // boundary: reset del form
    }

    @Override
    public void reindirizzaAllaHome() {
        // boundary: navigazione alla home
    }

    // === IVisualizzaProfilo ===

    @Override
    public BaseUtente visualizzaProfilo(Integer id) {
        return utenteService.getProfilo(id);
    }

    @Override
    public void mostraDatiProfilo(BaseUtente u) {
        // boundary: stampa/visualizza i dati
    }
}
