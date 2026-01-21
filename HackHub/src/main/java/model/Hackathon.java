package model;
import state.*;


import java.util.ArrayList;
import java.util.List;

public class Hackathon {
    private Long id;
    private String titolo;
    private Utente organizzatore;
    private Utente giudice;
    private List<Utente> mentori = new ArrayList<>();
    private List<Team> teamsIscritti = new ArrayList<>();


    private HackathonState statoCorrente;

    public Hackathon(Long id, String titolo, Utente organizzatore) {
        this.id = id;
        this.titolo = titolo;
        this.organizzatore = organizzatore;
        this.statoCorrente = new StatoInIscrizione(); // Stato iniziale
    }

    // Metodi che delegano allo Stato
    public void iscriviTeam(Team t) {
        statoCorrente.iscriviTeam(this, t);
    }

    public void chiudiIscrizioni() {
        statoCorrente.chiudiIscrizioni(this);
    }

    // Getter e Setter standard
    public void setStato(HackathonState stato) { this.statoCorrente = stato; }
    public List<Team> getTeamsIscritti() { return teamsIscritti; }
    public List<Utente> getMentori() { return mentori; }
    public void setGiudice(Utente giudice) { this.giudice = giudice; }
    public Long getId() { return id; }
    public String getTitolo() { return titolo; }
}
