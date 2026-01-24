package state;

import model.Hackathon;
import model.Team;

public class StatoInValutazione implements HackathonState{
    @Override
    public void iscriviTeam(Hackathon h, Team t) {
        throw new RuntimeException("ERRORE: Iscrizioni chiuse! L'evento è in valutazione.");

    }

    @Override
    public void nextState(Hackathon h) {
        h.setStato(new StatoConcluso());
        System.out.println("STATO CAMBIATO: Da In Valutazione -> Concluso");
    }

    @Override
    public String getNomeStato() {
        return "in Valutazione";
    }

    @Override
    public void uploadSottomissione(Hackathon h) {
        throw new RuntimeException("ERRORE: Non puoi più inviare la sottomissione, l'hackathon  è in fase di valutazione");
    }
}
