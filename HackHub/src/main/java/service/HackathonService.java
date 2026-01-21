package service;
import model.Hackathon;
import model.Utente;
import repository.HackathonRepository;

public class HackathonService {
    private HackathonRepository hackathonRepo;

    // Dependency Injection manuale (Constructor)
    public HackathonService(HackathonRepository hackathonRepo) {
        this.hackathonRepo = hackathonRepo;
    }

    public Hackathon creaHackathon(String titolo, Utente organizzatore) {
        // Logica di creazione
        long newId = System.currentTimeMillis(); // ID finto generato col tempo
        Hackathon h = new Hackathon(newId, titolo, organizzatore);

        hackathonRepo.save(h);
        return h;
    }

    public void setGiudice(Long hackathonId, Utente giudice) {
        Hackathon h = hackathonRepo.findById(hackathonId).orElseThrow();
        h.setGiudice(giudice);
        System.out.println("Service: Giudice assegnato.");
    }
}
