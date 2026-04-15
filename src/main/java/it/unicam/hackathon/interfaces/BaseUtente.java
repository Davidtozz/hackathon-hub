package it.unicam.hackathon.actors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class BaseUtente extends BaseModel{
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;
    protected String cellulare;
    protected Date dataIscrizione = new Date();
    /* TODO estrarre in un interfaccia dedicata
    public boolean login(String email, String password) { return true; }
    // Gestito da un servizio di autenticazione esterno, quindi non implementato qui
    public void registrazione(String nome, String cognome, String email, String password) { }
    public void visualizzaProfilo() { } */
}