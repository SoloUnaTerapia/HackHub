package state;
import model.Hackathon;
import model.Team;
import model.Utente;

public class StatoInCorso implements HackathonState {
    @Override
    public void iscriviTeam(Hackathon h, Team t) {
        throw new RuntimeException("ERRORE: Iscrizioni chiuse! L'evento è in corso.");
    }

    @Override
    public void aggiungiMentore(Hackathon h, Utente u) {
        System.out.println("LOG: Mentore aggiunto .");
        h.getMentori().add(u);
    }

    @Override
    public void chiudiIscrizioni(Hackathon h) {
        System.out.println("LOG: Le iscrizioni sono già chiuse.");
    }
}