package service;
import dto.HackathonDTO;
import model.Hackathon;
import model.Traccia;
import model.Utente;
import repository.HackathonRepository;
import repository.TracciaRepository;

public class HackathonService {
    private HackathonRepository hackathonRepo;
    private TracciaRepository tracciaRepo;

    // Dependency Injection manuale (Constructor)
    public HackathonService(HackathonRepository hackathonRepo, TracciaRepository tracciaRepo) {
        this.hackathonRepo = hackathonRepo; this.tracciaRepo=tracciaRepo;}

    public Hackathon creaHackathon(HackathonDTO dati, Utente organizzatore) {
        // Qui potresti validare le date (es. Fine > Inizio)
        if (dati.dataFine != null && dati.dataInizio != null && dati.dataFine.isBefore(dati.dataInizio)) {
            throw new RuntimeException("Errore Date: La fine non può essere prima dell'inizio!");
        }

        Hackathon h = new Hackathon(
                System.nanoTime(),
                dati.nome,
                dati.descrizione,
                dati.regolamento,
                dati.luogo,
                dati.premio,
                dati.maxTeamSize,
                organizzatore
        );

        // Setto le date (se non le ho messe nel costruttore per brevità)
        // h.setDataInizio(dati.dataInizio); ...

        hackathonRepo.save(h);
        return h;
    }
public void avanzaStato(Long hackathonId){
    Hackathon h = hackathonRepo.findById(hackathonId)
            .orElseThrow(() -> new RuntimeException("Hackathon non trovato"));

    // Il Service comanda all'Entity di avanzare
    h.passaAlProssimoStato();

    // Salva il cambiamento
    hackathonRepo.save(h);
}
    public void setGiudice(Long hackathonId, Utente giudice) {
        Hackathon h = hackathonRepo.findById(hackathonId).orElseThrow();
        h.setGiudice(giudice);
        System.out.println("Service: Giudice assegnato.");
    }
    public void aggiungiMentore(Long hackathonId, Utente mentore) {
        Hackathon h = hackathonRepo.findById(hackathonId).orElseThrow();
        h.setMentore(mentore);
        System.out.println("Service: mentore assegnato.");
    }
    public void aggiungiTraccia(Long hackathonId, String titolo, String desc) {
        Hackathon h = hackathonRepo.findById(hackathonId).orElseThrow();

        // Controllo: le tracce si danno solo prima o durante, non dopo
        // (Usa lo state pattern o un controllo semplice qui)

        Traccia t = new Traccia(System.nanoTime(), titolo, desc, h);
        tracciaRepo.save(t);
        System.out.println("TRACCIA CREATA: " + titolo + " per l'evento " + h.getNome());
    }
}
