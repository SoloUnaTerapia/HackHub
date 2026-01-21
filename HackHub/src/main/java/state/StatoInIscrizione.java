package state;
import model.Hackathon;
import model.Team;
import model.Utente;

public class StatoInIscrizione implements HackathonState {
    @Override
    public void iscriviTeam(Hackathon h, Team t) {
        System.out.println("LOG: Iscrizione team " + t.getNome() + " accettata.");
        h.getTeamsIscritti().add(t);
    }

    @Override
    public void aggiungiMentore(Hackathon h, Utente u) {
        System.out.println("LOG: Mentore aggiunto.");
        h.getMentori().add(u);
    }

    @Override
    public void chiudiIscrizioni(Hackathon h) {
        System.out.println("LOG: Chiusura iscrizioni... passaggio a In Corso.");
        h.setStato(new StatoInCorso()); // Cambio di stato!
    }
}
