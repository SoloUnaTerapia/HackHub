package state;
import model.Hackathon;
import model.Team;
import model.Utente;

public class StatoInIscrizione implements HackathonState {
    @Override
    public void iscriviTeam(Hackathon h, Team t) {
        h.getTeamsIscritti().add(t);
        System.out.println("LOG: Iscrizione team " + t.getNome() + " accettata.");
    }



    @Override
    public void nextState(Hackathon h) {
        h.setStato(new StatoInCorso());
        System.out.println("STATO CAMBIATO: Da In Iscrizione -> In Corso");
    }

    @Override
    public String getNomeStato() {
        return "in iscrizione";
    }
}
