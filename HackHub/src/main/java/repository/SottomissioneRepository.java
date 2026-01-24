package repository;
import model.Sottomissione;
import model.Team;
import model.Traccia;
import java.util.Optional;

public interface SottomissioneRepository {
    void save(Sottomissione s);

    // CORREZIONE: Cerchiamo per Team e Traccia
    Optional<Sottomissione> findByTeamAndTraccia(Team t, Traccia tr);
}