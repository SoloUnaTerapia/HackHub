package adapter;

public class ConsoleEmailAdapter implements EmailService{
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("--- [EMAIL SIMULATA] ---");
        System.out.println("A: " + to);
        System.out.println("OGGETTO: " + subject);
        System.out.println("TESTO: " + body);
        System.out.println("------------------------");
    }
}
