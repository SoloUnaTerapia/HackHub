package model;

public class Traccia {
    private Long id;
    private String titolo;       // Es. "Sviluppo Backend"
    private String descrizione;  // Es. "Usare Java e Spring Boot"
    private Hackathon hackathon; // A quale evento appartiene

    public Traccia(Long id, String titolo, String descrizione, Hackathon hackathon) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.hackathon = hackathon;
    }

    public Long getId() { return id; }
    public String getTitolo() { return titolo; }
    public Hackathon getHackathon() { return hackathon; }
}