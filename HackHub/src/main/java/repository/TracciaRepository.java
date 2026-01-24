package repository;
import model.Invito;
import model.Traccia;

import java.util.List;
import java.util.Optional;

public interface TracciaRepository {
    void save(Traccia t);
    Optional<Traccia> findById(Long id);
    List<Traccia> findAll(); // <--- ECCOLO

}
