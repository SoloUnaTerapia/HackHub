package repository.implementation;
import model.Sottomissione;
import model.Team;
import model.Traccia;
import repository.SottomissioneRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemorySottomissioneRepository implements SottomissioneRepository {
    private List<Sottomissione> db = new ArrayList<>();

    @Override
    public void save(Sottomissione s) {
        db.removeIf(old -> old.getId().equals(s.getId()));
        db.add(s);
    }

    @Override
    public Optional<Sottomissione> findByTeamAndTraccia(Team t, Traccia tr) {
        return db.stream()
                // ERRORE RISOLTO QUI: Uso getTraccia() invece di getHackathon()
                .filter(s -> s.getTeam().equals(t) && s.getTraccia().equals(tr))
                .findFirst();
    }
}