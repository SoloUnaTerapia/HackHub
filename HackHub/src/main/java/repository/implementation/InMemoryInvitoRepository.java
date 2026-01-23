package repository.implementation;

import model.Invito;
import model.Utente;
import repository.InvitoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryInvitoRepository implements InvitoRepository {
private List<Invito> db=new ArrayList<>();
    @Override
    public void save(Invito i) {
        db.removeIf(old -> old.getId().equals(i.getId()));
        db.add(i);
        System.out.println("DB: Invito " + i.getId() + " salvato.");
    }

    @Override
    public Optional<Invito> findById(Long id) {
        return db.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    @Override
    public List<Invito> findByRiceventeAndStato(Utente ricevente, Invito.StatoInvito stato) {
        return db.stream()
                .filter(i -> i.getRicevente().equals(ricevente)) // Filtra per Utente
                .filter(i -> i.getStato() == stato)              // Filtra per Stato
                .collect(Collectors.toList());                   // Ritorna la lista
    }

    @Override
    public List<Invito> findAll() {
        return new ArrayList<>(db);    }

    @Override
    public List<Invito> findByRicevente(Utente ricevente) {
        return db.stream()
                .filter(i -> i.getRicevente().equals(ricevente))
                .collect(Collectors.toList());
    }
}
