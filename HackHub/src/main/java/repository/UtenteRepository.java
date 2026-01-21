package repository;
import model.Utente;

import java.util.Optional;

public interface UtenteRepository {
    void save(Utente u);
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findById(Long id);
}
