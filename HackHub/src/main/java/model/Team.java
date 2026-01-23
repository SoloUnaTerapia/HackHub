package model;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String nome;
    private Utente leader;
    private List<Utente> membri = new ArrayList<>();

    public Team(Long id, String nome, Utente leader) {
        this.id = id;
        this.nome = nome;
        this.leader = leader;
        this.membri.add(leader);
        leader.setTeam(this);
    }


    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Utente getLeader() { return leader; }
    public List<Utente> getMembri() { return membri; }
}
