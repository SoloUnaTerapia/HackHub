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
    public void nextState(Hackathon h) {
        h.setStato(new StatoInValutazione());
        System.out.println("STATO CAMBIATO: Da In Corso -> In Valutazione");

    }

    @Override
    public String getNomeStato() {
        return "In corso";
    }
}