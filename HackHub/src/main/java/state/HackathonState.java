package state;
import model.Hackathon;
import model.Team;
import model.Utente;
public interface HackathonState {
    void iscriviTeam(Hackathon h, Team t);
    void aggiungiMentore(Hackathon h, Utente u);
    void chiudiIscrizioni(Hackathon h);
}
