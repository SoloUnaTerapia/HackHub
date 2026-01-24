package model;
import java.time.LocalDateTime;

public class Sottomissione {
    public enum StatoSottomissione { BOZZA, COMPLETATA }

    private Long id;
    private String descrizione;
    private String urlProgetto;
    private LocalDateTime dataInvio;
    private StatoSottomissione stato;
    private LocalDateTime dataUltimaModifica;


    private Team team;
    private Utente autore;

    // CORREZIONE: Teniamo SOLO la Traccia, togliamo Hackathon
    private Traccia traccia;

    public Sottomissione(Long id, Team team, Traccia traccia, Utente autore) {
        this.id = id;
        this.team = team;
        this.traccia = traccia;
        this.autore = autore;
        this.stato = StatoSottomissione.BOZZA;
        this.dataInvio = LocalDateTime.now();
    }

    public void lavora(String descrizione, String url, Utente autore) {
        this.descrizione = descrizione;
        this.urlProgetto = url;
        this.autore = autore;
        this.dataInvio = LocalDateTime.now();
    }

    public void completa() {
        this.stato = StatoSottomissione.COMPLETATA;
        this.dataUltimaModifica = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Team getTeam() { return team; }
    public Traccia getTraccia() { return traccia; }
    public StatoSottomissione getStato() { return stato; }
}