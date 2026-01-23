package service;
import model.*;
import repository.InvitoRepository;
import repository.UtenteRepository;

import java.util.List;

public class InvitoService {
    private InvitoRepository invitoRepo;
    private UtenteRepository utenteRepo; // Per salvare l'aggiornamento team

    public InvitoService(InvitoRepository iRepo, UtenteRepository uRepo) {
        this.invitoRepo = iRepo;
        this.utenteRepo = uRepo;
    }

    public void inviaInvito(Utente mittente, String emailRicevente, Team team) {
        // Cerca ricevente (simulato)
        Utente ricevente = utenteRepo.findByEmail(emailRicevente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Invito invito = new Invito(System.nanoTime(), mittente, ricevente, team);
        invitoRepo.save(invito);
        System.out.println("MAIL: Invito inviato a " + emailRicevente);
    }

    public void accettaInvito(Long idInvito) {
        Invito inv = invitoRepo.findById(idInvito).orElseThrow();

        // Logica Core: Aggiungi al team
        Team t = inv.getTeam();
        Utente u = inv.getRicevente();
        t.getMembri().add(u);
        u.setTeam(t); // Aggiorna utente

        inv.setStato(Invito.StatoInvito.ACCEPTED);

        invitoRepo.save(inv);
        utenteRepo.save(u); // Salva modifica utente
        System.out.println("INVITO: " + u.getNome() + " ora fa parte di " + t.getNome());
    }

    public List<Invito> getInvitiPendenti(Long idUtente) {
        // 1. Recupero l'oggetto Utente dall'ID (perché il repo vuole l'oggetto, non l'ID)
        Utente ricevente = utenteRepo.findById(idUtente)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // 2. Chiedo al Repository gli inviti PENDING per questo utente
        return invitoRepo.findByRiceventeAndStato(ricevente, Invito.StatoInvito.PENDING);
    }
}