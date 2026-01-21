package model;

public class Utente {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Team team;

    public Utente(Long id, String nome, String cognome, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    // Getter e Setter sono obbligatori! (Ne metto alcuni per brevità, tu falli tutti)
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNome() {return nome;}
    public String getCognome() { return cognome;}
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    @Override
    public String toString() { return nome + " " + cognome; }
}
