package repository;
import model.Utente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUtenteRepository implements UtenteRepository {
    private List<Utente> db = new ArrayList<>();

    @Override
    public void save(Utente u) {
        db.add(u);
        System.out.println("DB: Utente " + u.getEmail() + " salvato.");
    }

    @Override
    public Optional<Utente> findByEmail(String email) {
        return db.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Utente> findById(Long id) {
        return db.stream().filter(u -> u.getId().equals(id)).findFirst();
    }
}
