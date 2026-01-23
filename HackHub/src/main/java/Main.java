

import adapter.ConsoleEmailAdapter;
import dto.HackathonDTO;
import model.*;
import repository.implementation.*;
import service.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("      HACKHUB - SYSTEM SIMULATION         ");
        System.out.println("==========================================\n");

        // -----------------------------------------------------------
        // 1. BOOTSTRAP (Dependency Injection Manuale)
        // -----------------------------------------------------------
        // Creiamo i Repository (Database in memoria)
        var utenteRepo = new InMemoryUtenteRepository();
        var hackRepo = new InMemoryHackathonRepository();
        var teamRepo = new InMemoryTeamRepository();
        var invitoRepo = new InMemoryInvitoRepository();

        // Creiamo l'Adapter (Sistema Esterno finto)
        var emailAdapter = new ConsoleEmailAdapter();

        // Creiamo i Service (La logica)
        var authService = new AuthService(utenteRepo);
        var hackService = new HackathonService(hackRepo);
        // Nota: TeamService usa anche InvitoRepo e EmailService per gestire gli inviti
        var invitoService = new InvitoService(invitoRepo, utenteRepo);
        var teamService = new TeamService(teamRepo, invitoRepo, hackRepo,emailAdapter);

        // (Nota: Ho passato invitoService dentro TeamService per comodità,
        // oppure puoi chiamare invitoService direttamente dal main, qui lo uso separato)

        // -----------------------------------------------------------
        // 2. SCENARIO: REGISTRAZIONE UTENTI (AuthService)
        // -----------------------------------------------------------
        System.out.println(">>> FASE 1: Registrazione Utenti");

        Utente diegoOrg = authService.registra("Diego", "diego@org.com", "admin123");
        Utente marioLeader = authService.registra("Mario", "mario@dev.com", "pass");
        Utente luigiMember = authService.registra("Luigi", "luigi@dev.com", "pass");
        Utente giudiceDredd = authService.registra("Dredd", "dredd@law.com", "law");
        Utente mentoreYoda = authService.registra("Yoda", "yoda@force.com", "force");

        System.out.println("Utenti registrati: 5\n");

        // -----------------------------------------------------------
        // 3. SCENARIO: CREAZIONE HACKATHON (HackathonService)
        // -----------------------------------------------------------
        System.out.println(">>> FASE 2: Creazione Hackathon");

        HackathonDTO datiEvento = new HackathonDTO(
                "Java Code Wars 2025",
                "Sfida all'ultimo bit",
                "Roma (Campus)",
                10000.00,
                3 // Max 3 persone per team
        );

        // Diego crea l'evento
        Hackathon hackathon = hackService.creaHackathon(datiEvento, diegoOrg);
        System.out.println("Hackathon creato: " + hackathon.getNome() + " [Stato: " + hackathon.getStato() + "]");

        // Diego assegna lo staff
        hackService.setGiudice(hackathon.getId(), giudiceDredd);
        hackService.aggiungiMentore(hackathon.getId(), mentoreYoda);

        // Test controllo duplicati: Diego prova a fare il mentore del suo evento
        try {
            hackService.aggiungiMentore(hackathon.getId(), diegoOrg);
        } catch (Exception e) {
            System.out.println("   [TEST SECURITY] Bloccato tentativo illegale: " + e.getMessage());
        }
        System.out.println();

        // -----------------------------------------------------------
        // 4. SCENARIO: CREAZIONE TEAM E INVITI (TeamService + InvitoService)
        // -----------------------------------------------------------
        System.out.println(">>> FASE 3: Gestione Team e Inviti");

        // Mario crea il team
        Team teamAlpha = teamService.creaTeam("Alpha Team", marioLeader);
        System.out.println("Team creato: " + teamAlpha.getNome() + " da " + teamAlpha.getLeader().getNome());

        // Mario invita Luigi
        System.out.println("Mario invita Luigi...");
        invitoService.inviaInvito(marioLeader, "luigi@dev.com", teamAlpha);

        // Luigi controlla la mail (Simuliamo che veda l'invito pendente)
        List<Invito> invitiLuigi = invitoService.getInvitiPendenti(luigiMember.getId());
        if (!invitiLuigi.isEmpty()) {
            Invito invitoRicevuto = invitiLuigi.get(0);
            System.out.println("Luigi ha ricevuto un invito per il team: " + invitoRicevuto.getTeam().getNome());

            // Luigi accetta
            invitoService.accettaInvito(invitoRicevuto.getId());
        }

        // Verifica membri
        System.out.println("Membri attuali di Alpha Team: " + teamAlpha.getMembri().size() + " (Attesi: 2)");
        System.out.println("Luigi fa parte del team? " + (luigiMember.getTeam() == teamAlpha));
        System.out.println();

        // -----------------------------------------------------------
        // 5. SCENARIO: ISCRIZIONE ALL'EVENTO (State Pattern)
        // -----------------------------------------------------------
        System.out.println(">>> FASE 4: Iscrizione e Ciclo di Vita");

        // TENTATIVO 1: Hackathon appena creato (StatoCreazione) -> DOVREBBE FALLIRE
        try {
            System.out.print("Tentativo iscrizione in fase CREAZIONE... ");
            teamService.iscriviTeamAdHackathon(teamAlpha.getId(), hackathon.getId());
        } catch (Exception e) {
            System.out.println("FALLITO (Corretto): " + e.getMessage());
        }

        // AVANZAMENTO STATO: L'organizzatore apre le iscrizioni
        hackService.avanzaStato(hackathon.getId()); // Passa a IN ISCRIZIONE
        System.out.println("--- Cambio Stato: " + hackathon.getStato() + " ---");

        // TENTATIVO 2: Ora dovrebbe funzionare
        try {
            System.out.print("Tentativo iscrizione in fase APERTA... ");
            teamService.iscriviTeamAdHackathon(teamAlpha.getId(), hackathon.getId());
            System.out.println("SUCCESSO!");
        } catch (Exception e) {
            System.out.println("ERRORE INATTESO: " + e.getMessage());
        }

        // Verifica iscrizione
        System.out.println("Team iscritti all'evento: " + hackathon.getTeamsIscritti().size());

        // AVANZAMENTO STATO: L'evento inizia (Iscrizioni chiuse)
        hackService.avanzaStato(hackathon.getId()); // Passa a IN CORSO
        System.out.println("--- Cambio Stato: " + hackathon.getStato() + " ---");

        // TENTATIVO 3: Un ritardatario prova a iscriversi (Creo team al volo)
        Utente ritardatario = authService.registra("Bob", "bob@late.com", "pw");
        Team teamLate = teamService.creaTeam("Late Ones", ritardatario);

        try {
            System.out.print("Tentativo iscrizione in fase IN CORSO... ");
            teamService.iscriviTeamAdHackathon(teamLate.getId(), hackathon.getId());
        } catch (Exception e) {
            System.out.println("FALLITO (Corretto): " + e.getMessage());
        }

        System.out.println("\n==========================================");
        System.out.println("      SIMULAZIONE COMPLETATA CON SUCCESSO ");
        System.out.println("==========================================");
    }
}