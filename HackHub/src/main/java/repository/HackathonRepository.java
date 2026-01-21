package repository;

import model.Hackathon;

import java.util.Optional;

public interface HackathonRepository {
    void save(Hackathon h);
    Optional<Hackathon> findById(Long id);
}
