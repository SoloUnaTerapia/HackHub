package state;
import model.Hackathon;
import model.Team;
import model.Utente;
public interface HackathonState {
    void iscriviTeam(Hackathon h, Team t);
    void nextState(Hackathon h);
    String getNomeStato();
}
