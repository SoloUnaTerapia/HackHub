package repository.implementation;

import model.Invito;
import model.Team;
import repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTeamRepository implements TeamRepository {
    private List<Team> db=new ArrayList<>();
    @Override
    public void save(Team t) {
        db.add(t);
        System.out.println("DB: Team " + t.getNome() + " salvato.");
    }

    @Override
    public Optional<Team> findById(Long id) {
        return db.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
