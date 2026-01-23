package state;

import model.Hackathon;
import model.Team;

public class StatoConcluso  implements HackathonState{
    @Override
    public void iscriviTeam(Hackathon h, Team t) {
        throw new RuntimeException("ERRORE: Iscrizioni chiuse! L'evento è conncluso");

    }

    @Override
    public void nextState(Hackathon h) {
        throw new RuntimeException("evento già concluso!");

    }

    @Override
    public String getNomeStato() {
        return "concluso";
    }
}
