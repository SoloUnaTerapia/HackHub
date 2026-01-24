package repository.implementation;
import model.Traccia;
import repository.TracciaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTracciaRepository implements TracciaRepository {

    // Questa è la lista che fa da "Database"
    private List<Traccia> db = new ArrayList<>();

    @Override
    public void save(Traccia t) {
        db.removeIf(old -> old.getId().equals(t.getId()));
        db.add(t);
    }

    @Override
    public Optional<Traccia> findById(Long id) {
        return db.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    // --- ECCO L'IMPLEMENTAZIONE CHE TI MANCA ---
    @Override
    public List<Traccia> findAll() {
        // Restituisco una copia della lista per sicurezza
        return new ArrayList<>(db);
    }
}
