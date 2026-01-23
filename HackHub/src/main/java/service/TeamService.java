package service;
import adapter.EmailService;
import model.Hackathon;
import model.Invito;
import model.Team;
import model.Utente;
import repository.HackathonRepository;
import repository.InvitoRepository;
import repository.TeamRepository;

public class TeamService {
    private TeamRepository teamRepo;
    private HackathonRepository hackRepo;
    private EmailService emailService;
    private InvitoRepository invitoRepo;


    public TeamService(TeamRepository tRepo, InvitoRepository iRepo, HackathonRepository hRepo, EmailService mail) {
        this.teamRepo = tRepo;
        this.invitoRepo = iRepo;
        this.emailService = mail;
        this.hackRepo=hRepo;
    }
    public Team creaTeam(String nome, Utente leader) {
        Team t = new Team(System.currentTimeMillis(), nome, leader);
        teamRepo.save(t);
        return t;
    }

    public void invitaUtente(Utente leader, Utente invitato, Team team) {
        // Controlli base
        if (!team.getLeader().equals(leader)) {
            throw new RuntimeException("Solo il leader può invitare!");
        }
        Invito invito = new Invito(System.currentTimeMillis(), leader, invitato, team);
        invitoRepo.save(invito);

        // Uso l'adapter mail!
        emailService.sendEmail(
                invitato.getEmail(),
                "Hai un invito!",
                "Ciao " + invitato + ", sei stato invitato nel team " + team.getNome()
        );
    }

    public void iscriviTeamAdHackathon(Long idTeam, Long idHackathon) {
        Team t = teamRepo.findById(idTeam).get();
        Hackathon h = hackRepo.findById(idHackathon).get();

        // Qui scatta il pattern State dentro Hackathon
        h.iscriviTeam(t);

        hackRepo.save(h);
        System.out.println("Successo: Team " + t.getNome() + " iscritto a " + h.getNome());
    }
}
