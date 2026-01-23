package dto;
import java.time.LocalDateTime;

public class HackathonDTO {
    public String nome;
    public String descrizione;
    public String regolamento;
    public String luogo;
    public double premio;
    public int maxTeamSize;
    public LocalDateTime dataInizio;
    public LocalDateTime dataFine;
    public LocalDateTime scadenzaIscrizioni;

    // Costruttore comodo per il test nel Main
    public HackathonDTO(String nome, String desc, String luogo, double premio, int maxTeamSize) {
        this.nome = nome;
        this.descrizione = desc;
        this.luogo = luogo;
        this.premio = premio;
        this.maxTeamSize = maxTeamSize;
        // Per semplicità nel main mettiamo date fisse, ma nel vero si passano
        this.dataInizio = LocalDateTime.now().plusDays(10);
        this.dataFine = LocalDateTime.now().plusDays(12);
        this.scadenzaIscrizioni = LocalDateTime.now().plusDays(5);
    }
}