

import model.Hackathon;
import model.Team;
import model.Utente;
import repository.InMemoryUtenteRepository;
import repository.InMemoryHackathonRepository;
import repository.HackathonRepository;
import service.HackathonService;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- AVVIO HACKHUB (JAVA VERSION) ---");

        // 1. Setup dei finti DB e Service
        InMemoryUtenteRepository utenteRepo = new InMemoryUtenteRepository();
        HackathonRepository hackRepo = new InMemoryHackathonRepository(); // Crea tu questa classe come ho fatto per Utente!
        HackathonService hackService = new HackathonService(hackRepo);

        // 2. Creazione Utenti (Simulazione Registrazione)
        Utente mario = new Utente(1L, "Mario", "Rossi", "mario@test.com", "1234");
        Utente luigi = new Utente(2L, "Luigi", "Verdi", "luigi@test.com", "1234");
        utenteRepo.save(mario);
        utenteRepo.save(luigi);

        // 3. Mario crea un Hackathon
        System.out.println("\n--- CREAZIONE EVENTO ---");
        Hackathon evento = hackService.creaHackathon("Java Cup 2025", mario);
        hackService.setGiudice(evento.getId(), luigi);

        // 4. Creazione Team e Iscrizione
        System.out.println("\n--- ISCRIZIONE TEAM ---");
        Team teamAlpha = new Team(100L, "Alpha Team", mario);

        // Provo a iscrivere il team
        try {
            evento.iscriviTeam(teamAlpha); // Deve funzionare!
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 5. Test del Pattern State: Chiudo iscrizioni e riprovo
        System.out.println("\n--- CAMBIO STATO ---");
        evento.chiudiIscrizioni(); // Passa a InCorso

        try {
            System.out.println("Tentativo di iscrivere un altro team...");
            evento.iscriviTeam(teamAlpha); // QUI DEVE FALLIRE!
        } catch (Exception e) {
            System.out.println("ECCEZIONE CATTURATA CORRETTAMENTE: " + e.getMessage());
        }
    }
}