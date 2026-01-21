package repository;
import model.Hackathon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryHackathonRepository implements HackathonRepository {
    private List<Hackathon> db = new ArrayList<>();

    @Override
    public void save(Hackathon u) {
        db.add(u);
        System.out.println("DB: Hackaton " + u.getTitolo() + " salvato.");
    }

    @Override
    public Optional<Hackathon> findById(Long id) {
        return db.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

}