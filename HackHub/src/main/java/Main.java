


import adapter.ConsoleEmailAdapter;
import dto.HackathonDTO;
import model.*;
import repository.implementation.*;
import service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("##########################################");
        System.out.println("      HACKHUB - SIMULAZIONE COMPLETA      ");
        System.out.println("##########################################\n");

        // =============================================================
        // 1. SETUP DELL'ARCHITETTURA (Cablaggio)
        // =============================================================
        // Repository (Database in memoria)
        var utenteRepo = new InMemoryUtenteRepository();
        var hackRepo = new InMemoryHackathonRepository();
        var teamRepo = new InMemoryTeamRepository();
        var invitoRepo = new InMemoryInvitoRepository();
        var tracciaRepo = new InMemoryTracciaRepository(); // <--- Nuovo
        var sottomissioneRepo = new InMemorySottomissioneRepository(); // <--- Nuovo

        // Adapter (Servizi Esterni)
        var emailAdapter = new ConsoleEmailAdapter();

        // Services (Logica di Business)
        var authService = new AuthService(utenteRepo);
        // Nota: HackathonService ora vuole anche TracciaRepo
        var hackService = new HackathonService(hackRepo, tracciaRepo);
        var invitoService = new InvitoService(invitoRepo, utenteRepo);
        var teamService = new TeamService(teamRepo, invitoRepo , hackRepo,emailAdapter);
        var sottomissioneService = new SottomissioneService(sottomissioneRepo, tracciaRepo, utenteRepo);

        // =============================================================
        // 2. REGISTRAZIONE UTENTI (Attori)
        // =============================================================
        System.out.println("--- [FASE 1] REGISTRAZIONE UTENTI ---");
        Utente diegoOrg = authService.registra("Diego", "diego@org.com", "admin");
        Utente marioLeader = authService.registra("Mario", "mario@team.com", "pass");
        Utente luigiMember = authService.registra("Luigi", "luigi@team.com", "pass");
        Utente giudiceDredd = authService.registra("Dredd", "judge@law.com", "law");
        Utente mentoreYoda = authService.registra("Yoda", "yoda@force.com", "force");
        System.out.println("Utenti pronti.\n");

        // =============================================================
        // 3. ORGANIZZATORE: CREAZIONE EVENTO E STAFF
        // =============================================================
        System.out.println("--- [FASE 2] CREAZIONE HACKATHON ---");

        HackathonDTO dati = new HackathonDTO(
                "Java Code Wars 2025",
                "Gara finale",
                "Online",
                5000.0,
                4 // Max membri
        );

        Hackathon hackathon = hackService.creaHackathon(dati, diegoOrg);
        System.out.println("Hackathon creato: " + hackathon.getNome() + " (Stato: " + hackathon.getStato() + ")");

        // Assegnazione Staff
        hackService.setGiudice(hackathon.getId(), giudiceDredd);
        hackService.aggiungiMentore(hackathon.getId(), mentoreYoda);

        // Creazione Tracce (Compiti)
        hackService.aggiungiTraccia(hackathon.getId(), "Backend Challenge", "Creare un'API REST sicura");
        hackService.aggiungiTraccia(hackathon.getId(), "Frontend Challenge", "Creare una UI in React");
        System.out.println("Staff assegnato e Tracce create.\n");

        // =============================================================
        // 4. UTENTI: CREAZIONE TEAM E INVITI
        // =============================================================
        System.out.println("--- [FASE 3] CREAZIONE TEAM ---");

        // Mario crea il team
        Team teamAlpha = teamService.creaTeam("Alpha Team", marioLeader);

        // Mario invita Luigi
        invitoService.inviaInvito(marioLeader, "luigi@team.com", teamAlpha);

        // Luigi accetta (Simulazione: prendiamo il primo invito pendente)
        Invito invitoPerLuigi = invitoRepo.findByRicevente(luigiMember).get(0);
        invitoService.accettaInvito(invitoPerLuigi.getId());

        System.out.println("Team Alpha composto da: " + teamAlpha.getMembri().size() + " membri.\n");

        // =============================================================
        // 5. CICLO DI VITA: APERTURA ISCRIZIONI
        // =============================================================
        System.out.println("--- [FASE 4] APERTURA ISCRIZIONI ---");

        // L'organizzatore apre le iscrizioni
        System.out.println("Stato attuale: " + hackathon.getStato());

        // Il Team si iscrive
        teamService.iscriviTeamAdHackathon(teamAlpha.getId(), hackathon.getId());
        System.out.println("Team Alpha iscritto con successo!\n");

        // =============================================================
        // 6. CICLO DI VITA: INIZIO GARA (Svolgimento)
        // =============================================================
        System.out.println("--- [FASE 5] INIZIO GARA ---");

        // L'evento passa "In Corso"
        hackService.avanzaStato(hackathon.getId());
        System.out.println("Stato attuale: " + hackathon.getStato());

        // Recuperiamo una traccia su cui lavorare
        Traccia tracciaBackend = tracciaRepo.findAll().get(0);

        // Luigi (Membro) invia una BOZZA
        System.out.println(">> Luigi invia una bozza...");
        sottomissioneService.consegnaLavoro(
                tracciaBackend.getId(),
                luigiMember.getId(),
                "Inizio lavori controller",
                "github.com/wip",
                false // Non definitivo
        );

        // Mario (Leader o Membro) invia la versione DEFINITIVA
        System.out.println(">> Mario invia la versione finale...");
        sottomissioneService.consegnaLavoro(
                tracciaBackend.getId(),
                marioLeader.getId(),
                "Lavoro completato e testato",
                "github.com/final-release",
                true // Definitivo
        );

        System.out.println("\n##########################################");
        System.out.println("      TEST COMPLETATO SENZA ERRORI        ");
        System.out.println("##########################################");
    }
}