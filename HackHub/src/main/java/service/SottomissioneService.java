package service;
// ... imports ...

import model.*;
import repository.SottomissioneRepository;
import repository.TracciaRepository;
import repository.UtenteRepository;


import java.util.Optional;

// ... imports ...

public class SottomissioneService {
    private SottomissioneRepository sottomissioneRepo;
    private TracciaRepository tracciaRepo; // Serve per caricare la traccia
    private UtenteRepository utenteRepo;

    public SottomissioneService(SottomissioneRepository sRepo, TracciaRepository tRepo, UtenteRepository uRepo) {
        this.sottomissioneRepo = sRepo;
        this.tracciaRepo = tRepo;
        this.utenteRepo = uRepo;
    }

    public void consegnaLavoro(Long idTraccia, Long idUtente, String testo, String url, boolean definita) {
        // 1. Recupero Dati
        Traccia traccia = tracciaRepo.findById(idTraccia)
                .orElseThrow(() -> new RuntimeException("Traccia non trovata"));
        Utente autore = utenteRepo.findById(idUtente).orElseThrow();
        Team team = autore.getTeam();

        // 2. Controllo Stato Hackathon
        // CORREZIONE: Recupero l'hackathon dalla traccia!
        Hackathon h = traccia.getHackathon();

        // Controllo che il team sia iscritto a QUESTO hackathon
        if (!h.getTeamsIscritti().contains(team)) {
            throw new RuntimeException("Il tuo team non è iscritto a questo evento!");
        }

        // Controllo che l'evento sia IN CORSO (Pattern State)
        h.getStato().uploadSottomissione(h);

        // 3. Logica Crea o Aggiorna (Uso il nuovo metodo del repo)
        Optional<Sottomissione> esistente = sottomissioneRepo.findByTeamAndTraccia(team, traccia);

        Sottomissione s;
        if (esistente.isPresent()) {
            s = esistente.get();
            if (s.getStato() == Sottomissione.StatoSottomissione.COMPLETATA) {
                throw new RuntimeException("Hai già consegnato il lavoro definitivo!");
            }
            s.lavora(testo, url, autore);
            System.out.println("LOG: Bozza aggiornata.");
        } else {
            // Creo nuova passando la TRACCIA, non l'hackathon
            s = new Sottomissione(System.nanoTime(), team, traccia, autore);
            s.lavora(testo, url, autore);
            System.out.println("LOG: Nuova bozza creata.");
        }

        if (definita) {
            s.completa();
            System.out.println("LOG: CONSEGNA DEFINITIVA EFFETTUATA.");
        }

        sottomissioneRepo.save(s);
    }
}