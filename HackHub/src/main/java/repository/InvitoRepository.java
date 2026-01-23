package repository;

import model.Invito;
import model.Team;
import model.Utente;

import java.util.List;
import java.util.Optional;

public interface InvitoRepository {
    void save(Invito i);
    Optional<Invito> findById(Long id);
    List<Invito> findByRiceventeAndStato(Utente ricevente, Invito.StatoInvito stato);
    List<Invito> findAll(); // <--- ECCOLO
    List<Invito> findByRicevente(Utente ricevente);
}
