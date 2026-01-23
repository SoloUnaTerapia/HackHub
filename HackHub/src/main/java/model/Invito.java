package model;

public class Invito {
    public enum StatoInvito { PENDING, ACCEPTED, REJECTED }

    private Long id;
    private Utente mittente;
    private Utente ricevente;
    private Team team;
    private StatoInvito stato;

    public Invito(Long id, Utente mittente, Utente ricevente, Team team) {
        this.id = id;
        this.mittente = mittente;
        this.ricevente = ricevente;
        this.team = team;
        this.stato = StatoInvito.PENDING;
    }

    // Getter e Setter
    public Long getId() { return id; }
    public Utente getRicevente() { return ricevente; }
    public Team getTeam() { return team; }
    public StatoInvito getStato() { return stato; }
    public void setStato(StatoInvito stato) { this.stato = stato; }
}
