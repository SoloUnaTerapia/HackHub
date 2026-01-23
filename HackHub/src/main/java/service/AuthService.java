package service;
import model.Utente;
import repository.UtenteRepository;

public class AuthService {
    private UtenteRepository repo;

    public AuthService(UtenteRepository repo) { this.repo = repo; }

    public Utente registra(String nome,  String email, String pass) {
        Utente u = new Utente(System.nanoTime(), nome,  email, pass);
        repo.save(u);
        return u;
    }

    public Utente login(String email, String pass) {
        return repo.findByEmail(email) // Assumi che il metodo esista nell'interfaccia o fai un filtro qui
                .filter(u -> u.getPassword().equals(pass))
                .orElseThrow(() -> new RuntimeException("Login Fallito"));
    }
}