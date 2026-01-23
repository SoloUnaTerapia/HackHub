package model;
import state.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hackathon {
    private Long id;
    private String nome;
    private String descrizione;
    private String regolamento;
    private String luogo;
    private double premio;
    private int maxTeamSize;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Utente organizzatore;
    private Utente giudice;
    private List<Utente> mentori = new ArrayList<>();
    private List<Team> teamsIscritti = new ArrayList<>();
    private HackathonState statoCorrente;

    public Hackathon(Long id, String nome, String descrizione, String regolamento,
                     String luogo, double premio, int maxTeamSize, Utente organizzatore) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.regolamento = regolamento;
        this.luogo = luogo;
        this.premio = premio;
        this.maxTeamSize = maxTeamSize;
        this.organizzatore = organizzatore;
        this.statoCorrente = new StatoInIscrizione();
    }

    // Metodi che delegano allo Stato
    public void iscriviTeam(Team t) {
        // Controllo strutturale PRIMA di chiamare lo stato
        if (t.getMembri().size() > this.maxTeamSize) {
            throw new RuntimeException("ERRORE: Il team è troppo numeroso! Max: " + maxTeamSize);
        }

        // Delega allo stato (che controlla se siamo nel periodo giusto)
        statoCorrente.iscriviTeam(this, t);
    }


    public void passaAlProssimoStato() {
        statoCorrente.nextState(this);
    }

    // Getter e Setter standard
    public void setStato(HackathonState s) { this.statoCorrente = s; }
    public HackathonState getStato(){return this.statoCorrente;}
    public List<Team> getTeamsIscritti() { return teamsIscritti; }
    public List<Utente> getMentori() { return mentori; }
    public void setGiudice(Utente giudice) { this.giudice = giudice; }
    public void setMentore(Utente mentore) {this.mentori.add(mentore);}
    public Long getId() { return id; }
    public String getNome() { return nome; }


}
