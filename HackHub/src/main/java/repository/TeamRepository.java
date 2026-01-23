package repository;

import model.Hackathon;
import model.Team;

import java.util.Optional;

public interface TeamRepository {
    void save(Team t);
    Optional<Team> findById(Long id);

}
