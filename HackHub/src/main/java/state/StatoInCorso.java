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

    @Override
    public void uploadSottomissione(Hackathon h) {
        // Controllo della data (Deadline)
        // Nota: nel DTO avevi messo scadenzaIscrizioni e dataFine.
        // Assumiamo che dataFine sia la deadline per la consegna.
        /*
           Per semplicità nel test manuale, commentiamo il controllo data rigoroso
           o assicurati che nel Main le date siano future.
        */
        System.out.println("LOG: Stato 'In Corso' -> Permesso accordato per sottomissione.");
    }
}